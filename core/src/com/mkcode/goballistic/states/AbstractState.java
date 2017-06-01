package com.mkcode.goballistic.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractState {

	protected Texture backgroundImage;
	
	protected void render(SpriteBatch batch) {
		
		batch.draw(backgroundImage, 0, 0);
	}
}
