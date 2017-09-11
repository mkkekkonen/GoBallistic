package com.mkcode.goballistic.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.mkcode.goballistic.util.Constants;

public class HighScoreManager {
	
	HighScores highScores;
	
	public HighScoreManager() {
		highScores = deserialize();
	}
	
	public void updateHighScores(PlayerScore playerScore) {
		highScores.addToHighScores(playerScore);
		serialize(highScores);
	}
	
	public int getRanking(PlayerScore playerScore) {
		List highScoreList = highScores.getHighScoreList();
		return highScoreList.size() - highScoreList.indexOf(playerScore) - 1;
	}
	
	public void serialize(HighScores highScores) {
		// check that the file exists, create it if it doesn't
		File file = new File(Constants.HIGH_SCORES_FILE);
		System.out.println(file.getAbsolutePath());
		try {
			file.createNewFile();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			// initialize streams
			fileOutputStream = new FileOutputStream(Constants.HIGH_SCORES_FILE, false);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(highScores); // write high scores to the file
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			// try to close the streams
			if(fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
			if(objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public HighScores deserialize() {
		HighScores highScores = null;
		// check that the file exists and is not a directory
		File file = new File(Constants.HIGH_SCORES_FILE);
		if(file.exists() && !file.isDirectory()) {
			FileInputStream fileInputStream = null;
			ObjectInputStream objectInputStream = null;
			try {
				// initialize streams
				fileInputStream = new FileInputStream(Constants.HIGH_SCORES_FILE);
				objectInputStream = new ObjectInputStream(fileInputStream);
				highScores = (HighScores)objectInputStream.readObject(); // read high scores from the file
			} catch(Exception ex) {
				// exception - create new empty high scores
				highScores = new HighScores();
				ex.printStackTrace();
			} finally {
				// try to close the streams
				if(fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch(IOException ex) {
						ex.printStackTrace();
					}
				}
				if(objectInputStream != null) {
					try {
						objectInputStream.close();
					} catch(IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		else // file doesn't exist, create new empty high scores
			highScores = new HighScores();
		return highScores;
	}
	
	public List<PlayerScore> getHighScoreList() {
		return highScores.getHighScoreList();
	}
}
