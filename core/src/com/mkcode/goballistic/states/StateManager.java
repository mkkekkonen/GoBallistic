package com.mkcode.goballistic.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.score.HighScoreManager;

public class StateManager {

	Map<String, AbstractState> gameStates;	// dictionary of game states
	
	String currentState = "mainMenu"; // the first state in the game
	
	public StateManager(FontManager fontManager, HighScoreManager highScoreManager) {
		this.gameStates = new HashMap<String, AbstractState>(); // initialize game state dictionary
		this.gameStates.put("mainMenu", new MainMenuState(this, fontManager));
		this.gameStates.put("gamePlaying", new GamePlayingState(this, fontManager));
		this.gameStates.put("scoreboard", new ScoreboardState(this, fontManager, highScoreManager));
		this.gameStates.put("highScores", new HighScoresState(this, fontManager, highScoreManager));
	}
	
	public void render(SpriteBatch batch) {
		this.gameStates.get(this.currentState).render(batch);	// render current game state
	}
	
	public void update(float deltaTime) {
		this.gameStates.get(this.currentState).update(deltaTime); // update (physics etc.) with time delta
	}
	
	public void dispose() {
		for(Map.Entry<String, AbstractState> entry : this.gameStates.entrySet()) // loop through game states
			entry.getValue().dispose();	// dispose of each game state
	}
	
	public void changeState(String newState) {
		this.currentState = newState;
		if(newState == "scoreboard") {
			ScoreboardState sbState = (ScoreboardState)this.gameStates.get(newState);
			GamePlayingState gpState = (GamePlayingState)this.gameStates.get("gamePlaying");
			sbState.setScore(gpState.getScore());
		}
		if(newState == "gamePlaying" || newState == "scoreboard")
			this.gameStates.get(newState).init();
	}
}
