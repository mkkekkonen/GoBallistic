package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.input.NameInputListener;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.Score;
import com.mkcode.goballistic.ui.ExitButton;
import com.mkcode.goballistic.util.Constants;

public class ScoreboardState extends AbstractState {

	private NameInputListener inputListener;
	private String name;
	private BitmapFont titleFont, statsFont;
	private ExitButton exitButton;
	private Score score = null;
	
	public ScoreboardState(StateManager stateManager, FontManager fontManager) {
		super(stateManager, fontManager);
		inputListener = new NameInputListener(this);
		titleFont = fontManager.getFont("titleFont");
		statsFont = fontManager.getFont("font35");
		exitButton = new ExitButton(new Vector2(
				Constants.WND_WIDTH - Constants.EXIT_BUTTON_W - Constants.EXIT_BUTTON_MARGIN, 
				Constants.EXIT_BUTTON_MARGIN
		));
	}

	@Override
	public void update(float deltaTime) {
		if(Gdx.input.isTouched() && name != null) {
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			if(exitButton.getRect().containsPoint(Gdx.input.getX(), yCoord))
				stateManager.changeState("mainMenu");
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(name != null) {
			titleFont.draw(batch, Resources.tr("stats"), 30, 480 - 30);
			titleFont.draw(batch, name, 30, 480 - 80);
			
			if(score != null) {
				statsFont.draw(batch, Resources.tr("time"), 40, 480 - 120);
				statsFont.draw(batch, score.getFormattedTime(), 320, 480 - 120);
				
				statsFont.draw(batch, Resources.tr("shots"), 40, 480 - 160);
				statsFont.draw(batch, Integer.toString(score.getShots()), 320, 480 - 160);
				
				statsFont.draw(batch, Resources.tr("points"), 40, 480 - 200);
				statsFont.draw(batch, Integer.toString(calculatePoints()), 320, 480 - 200);
				
				statsFont.draw(batch, Resources.tr("ranking"), 40, 480 - 240);
				statsFont.draw(batch, Resources.tr("ranking"), 320, 480 - 240);
			}
			
			exitButton.render(batch);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		exitButton.dispose();
	}
	
	public void init() {
		name = null;
		Gdx.input.getTextInput(inputListener, Resources.tr("typeName"), "", "");
	}
	
	public void setScore(Score score) {
		this.score = score;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Calculates the player's points by calculating the inverse numbers 
	 * of shots and time (in seconds), adding them together, and multiplying 
	 * them by 10,000.
	 * @return int points
	 */
	private int calculatePoints() {
		float inverseShots = 1 / score.getShots();
		float inverseTime = 1 / (score.getTime() / 1000f);
		int points = (int)((inverseShots + inverseTime) * 10000); 
		System.out.println("Inverse shots: " + inverseShots);
		System.out.println("Inverse time: " + inverseTime);
		System.out.println("Points: " + points);
		return points;
	}
}
