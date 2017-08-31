package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.util.Constants;

public abstract class AbstractState {

	protected Texture backgroundImage;
	
	protected FontManager fontManager;
	
	public AbstractState(FontManager fontManager) {
		this.backgroundImage = new Texture(Gdx.files.internal("bg.png"));
		this.fontManager = fontManager;
	}
	
	public void render(SpriteBatch batch) {
		
		batch.draw(backgroundImage, 0, 0);
	}
	
	public void update(float deltaTime) {
		
	}
	
	public void dispose() {
		backgroundImage.dispose();
	}
}
