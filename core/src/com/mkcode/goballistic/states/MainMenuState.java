package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.ui.TextButton;
import com.mkcode.goballistic.util.Constants;
import com.mkcode.mousefix.Mouse;

public class MainMenuState extends AbstractState {

	private BitmapFont titleFont, menuFont;
	private TextButton playButton, highScoresButton;
	
	public MainMenuState(StateManager stateManager, FontManager fontManager) {
		super(stateManager, fontManager);
		displayExitButton = false;
		titleFont = fontManager.getFont("titleFont");
		menuFont = fontManager.getFont("font35");
		playButton = new TextButton(new Vector2(Constants.MAIN_MENU_BUTTON_OFFSET_X, Constants.MAIN_MENU_BUTTON_PLAY_Y), menuFont, "newGame");
		highScoresButton = new TextButton(new Vector2(Constants.MAIN_MENU_BUTTON_OFFSET_X, Constants.MAIN_MENU_BUTTON_HISCORES_Y), menuFont, "highScores");
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		titleFont.draw(batch, Resources.tr("GoBallistic"), Constants.TITLE_OFFSET_X, Constants.TITLE_OFFSET_Y);
		playButton.render(batch);
		highScoresButton.render(batch);
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.clicked()) {
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			Vector2 mouseLocation = new Vector2(Gdx.input.getX(), yCoord);
			if(playButton.getRect().containsPoint(mouseLocation))
				stateManager.changeState("gamePlaying");
			else if(highScoresButton.getRect().containsPoint(mouseLocation))
				stateManager.changeState("highScores");
		}
	}
}
