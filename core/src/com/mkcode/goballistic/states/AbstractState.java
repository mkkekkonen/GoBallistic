package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.ui.ExitButton;
import com.mkcode.goballistic.util.Constants;
import com.mkcode.mousefix.Mouse;

public abstract class AbstractState {

	protected Texture backgroundImage;
	protected ExitButton exitButton;
	
	protected StateManager stateManager;
	protected FontManager fontManager;
	
	protected boolean displayExitButton;
	
	public AbstractState(StateManager stateManager, FontManager fontManager) {
		this.backgroundImage = new Texture(Gdx.files.internal("bg.png"));
		this.exitButton = new ExitButton();
		this.stateManager = stateManager;
		this.fontManager = fontManager;
	}
	
	public void init() {
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(backgroundImage, 0, 0);
		if(displayExitButton)
			exitButton.render(batch);
	}
	
	public void update(float deltaTime) {
		if(Mouse.clicked()) {
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			if(displayExitButton && exitButton.getRect().containsPoint(new Vector2(Gdx.input.getX(), yCoord)))
				stateManager.changeState("mainMenu");
		}
	}
	
	public void dispose() {
		backgroundImage.dispose();
	}
}
