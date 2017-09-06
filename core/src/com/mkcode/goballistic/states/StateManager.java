package com.mkcode.goballistic.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;

public class StateManager {

	Map<String, AbstractState> gameStates;	// dictionary of game states
	
	String currentState = "mainMenu"; // the first state in the game
	
	public StateManager(FontManager fontManager) {
		this.gameStates = new HashMap<String, AbstractState>(); // initialize game state dictionary
		this.gameStates.put("mainMenu", new MainMenuState(this, fontManager));
		this.gameStates.put("gamePlaying", new GamePlayingState(this, fontManager));
		this.gameStates.put("scoreboard", new ScoreboardState(this, fontManager));
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
		if(newState == "gamePlaying" || newState == "scoreboard")
			this.gameStates.get(newState).init();
		if(newState == "scoreboard") {
			ScoreboardState sbState = (ScoreboardState)this.gameStates.get(newState);
			GamePlayingState gpState = (GamePlayingState)this.gameStates.get("gamePlaying");
			sbState.setScore(gpState.getScore());
		}
	}
}
