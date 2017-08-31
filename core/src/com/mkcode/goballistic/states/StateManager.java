package com.mkcode.goballistic.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;

public class StateManager {

	Map<String, AbstractState> gameStates;	// dictionary of game states
	
	String currentState = "gamePlaying";
	
	public StateManager(FontManager fontManager) {
		gameStates = new HashMap<String, AbstractState>(); // initialize game state dictionary
		gameStates.put("gamePlaying", new GamePlayingState(fontManager)); // initialize "game playing" state,
		 																  // pass in fontManager
	}
	
	public void render(SpriteBatch batch) {
		gameStates.get(currentState).render(batch);	// render current game state
	}
	
	public void update(float deltaTime) {
		gameStates.get(currentState).update(deltaTime); // update (physics etc.) with time delta
	}
	
	public void dispose() {
		for(Map.Entry<String, AbstractState> entry : gameStates.entrySet()) // loop through game states
			entry.getValue().dispose();	// dispose of each game state
	}
	
	public void changeState(String newState) {
		
	}
}
