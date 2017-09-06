package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.ui.MainMenuButton;
import com.mkcode.goballistic.util.Constants;

public class MainMenuState extends AbstractState {

	private BitmapFont titleFont, menuFont;
	private MainMenuButton playButton, highScoresButton;
	
	public MainMenuState(StateManager stateManager, FontManager fontManager) {
		super(stateManager, fontManager);
		this.titleFont = this.fontManager.getFont("titleFont");
		this.menuFont = this.fontManager.getFont("font35");
		this.playButton = new MainMenuButton(new Vector2(Constants.MAIN_MENU_BUTTON_OFFSET_X, Constants.MAIN_MENU_BUTTON_PLAY_Y), menuFont, "newGame");
		this.highScoresButton = new MainMenuButton(new Vector2(Constants.MAIN_MENU_BUTTON_OFFSET_X, Constants.MAIN_MENU_BUTTON_HISCORES_Y), menuFont, "highScores");
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		this.titleFont.draw(batch, Resources.tr("GoBallistic"), Constants.TITLE_OFFSET_X, Constants.TITLE_OFFSET_Y);
		this.playButton.render(batch);
		this.highScoresButton.render(batch);
	}

	@Override
	public void update(float deltaTime) {
		if(Gdx.input.isTouched()) {
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			if(playButton.getRect().containsPoint(new Vector2(Gdx.input.getX(), yCoord))) {
				stateManager.changeState("gamePlaying");
			}
		}
	}
}
