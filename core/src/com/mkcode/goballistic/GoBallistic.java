package com.mkcode.goballistic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.managers.FontManager;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.states.AbstractState;
import com.mkcode.goballistic.states.GamePlayingState;

public class GoBallistic extends ApplicationAdapter {
	
	SpriteBatch batch; 						// object that draws sprites
	Map<String, AbstractState> gameStates;	// dictionary of game states
	FontManager fontManager;				
	Resources resources;					// translation

	public long curTime, prevTime;			// frame/physics timing
	
	String currentState = "gamePlaying";

	/**
	 * Initialize the game
	 */
	@Override
	public void create () {
		batch = new SpriteBatch(); // object that draws sprites
		
		fontManager = new FontManager();
		resources = new Resources(); // translation
		
		prevTime = curTime = System.nanoTime();	// initialize frame/physics timing
		
		gameStates = new HashMap<String, AbstractState>(); // initialize game state dictionary
		gameStates.put("gamePlaying", new GamePlayingState(fontManager)); // initialize "game playing" state,
		 																  // pass in fontManager
	}

	/**
	 * Render/update loop
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);			// set clear background color (red)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	// clear background
		batch.begin();								// begin drawing sprites
		gameStates.get(currentState).render(batch);	// render current game state
		batch.end();								// end drawing sprites
		
		curTime = System.nanoTime();			// get current time in nanoseconds
		double nsPerFrame = curTime - prevTime;	// calculate frame time
		gameStates.get(currentState).update((float)(nsPerFrame / 1.0E9)); // update (physics etc.) with time delta
		prevTime = curTime;						// update previous time
	}
	
	/**
	 * Clean up
	 */
	@Override
	public void dispose () {
		batch.dispose();				// dispose of SpriteBatch
		for(Map.Entry<String, AbstractState> entry : gameStates.entrySet()) // loop through game states
			entry.getValue().dispose();	// dispose of each game state
		fontManager.dispose();			// dispose of FontManager
	}
}
