package com.mkcode.goballistic.score;

import java.io.Serializable;

public class PlayerScore implements Comparable<PlayerScore>, Serializable {

	private String name;
	private int score;
	
	public PlayerScore(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(PlayerScore otherPlayerScore) {
		return (this.score < otherPlayerScore.getScore()) ? -1 : (this.score > otherPlayerScore.getScore()) ? 1 : 0;
	}
}
