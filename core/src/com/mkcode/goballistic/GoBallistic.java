package com.mkcode.goballistic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.HighScoreManager;
import com.mkcode.goballistic.states.AbstractState;
import com.mkcode.goballistic.states.GamePlayingState;
import com.mkcode.goballistic.states.StateManager;
import com.mkcode.mousefix.Mouse;

public class GoBallistic extends ApplicationAdapter {
	
	SpriteBatch batch; 			// object that draws sprites
	FontManager fontManager;
	StateManager stateManager;	/* object that handles the different states of the game,
								 * such as menus, game playing etc. */
	HighScoreManager highScoreManager; // object that stores and serializes/deserializes high scores
	Resources resources;		// translation
	Music music;

	public long curTime, prevTime; // frame/physics timing

	/**
	 * Initialize the game
	 */
	@Override
	public void create () {
		batch = new SpriteBatch(); // object that draws sprites
		
		fontManager = new FontManager();
		resources = new Resources(); // translation
		highScoreManager = new HighScoreManager();
		stateManager = new StateManager(fontManager, highScoreManager); // initialize states
		
		// music
		music = Gdx.audio.newMusic(Gdx.files.internal("HeartOfMachine.ogg"));
		music.setLooping(true);
		music.play();
		
		prevTime = curTime = System.nanoTime();	// initialize frame/physics timing
		
	}

	/**
	 * Render/update loop
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);			// set clear background color (red)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	// clear background
		batch.begin();								// begin drawing sprites
		stateManager.render(batch);
		batch.end();								// end drawing sprites
		
		Mouse.update(); // update mouse fix class
		
		curTime = System.nanoTime();			// get current time in nanoseconds
		double nsPerFrame = curTime - prevTime;	// calculate frame time
//		stateManager.update((float)(nsPerFrame / 1.0E9)); // update game state, pass in time delta
		stateManager.update(0.01f);				// debug
		prevTime = curTime;						// update previous time
	}
	
	/**
	 * Clean up
	 */
	@Override
	public void dispose () {
		batch.dispose();
		stateManager.dispose();
		fontManager.dispose();
		music.stop(); // stop playing music
		music.dispose();
	}
}
