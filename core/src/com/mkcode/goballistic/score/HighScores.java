package com.mkcode.goballistic.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<PlayerScore> playerScoreList;
	
	public HighScores() {
		playerScoreList = new ArrayList<PlayerScore>();
	}

	public void addToHighScores(PlayerScore newPlayerScore) {
		playerScoreList.add(newPlayerScore);
		Collections.sort(playerScoreList);
		if(playerScoreList.size() > 10)
			playerScoreList.remove(10);
	}
	
	public List<PlayerScore> getHighScoreList() {
		return playerScoreList;
	}
}
