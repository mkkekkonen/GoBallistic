package com.mkcode.goballistic.score;

public class Score {

	private int shots;
	private long time, startTime;
	
	public Score() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void incrementShots() {
		this.shots++;
	}
	
	public void updateTime() {
		this.time = System.currentTimeMillis() - this.startTime;
	}
	
	public int getShots() {
		return shots;
	}
	
	public void setShots(int shots) {
		this.shots = shots;
	}
	
	public float getTime() {
		return time / 1000f;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
}
