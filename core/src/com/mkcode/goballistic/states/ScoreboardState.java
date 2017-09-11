package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.input.NameInputListener;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.HighScoreManager;
import com.mkcode.goballistic.score.PlayerScore;
import com.mkcode.goballistic.score.Score;
import com.mkcode.goballistic.ui.ExitButton;
import com.mkcode.goballistic.ui.TextButton;
import com.mkcode.goballistic.util.Constants;
import com.mkcode.mousefix.Mouse;

public class ScoreboardState extends AbstractState {

	private NameInputListener inputListener;
	private String name;
	private BitmapFont titleFont, statsFont;
	private Score score = null;
	private HighScoreManager highScoreManager;
	private TextButton highScoresButton;
	
	private int points, ranking;
	
	public ScoreboardState(StateManager stateManager, FontManager fontManager, HighScoreManager highScoreManager) {
		super(stateManager, fontManager);
		displayExitButton = true;
		inputListener = new NameInputListener(this);
		titleFont = fontManager.getFont("titleFont");
		statsFont = fontManager.getFont("font35");
		this.highScoreManager = highScoreManager;
		highScoresButton = new TextButton(new Vector2(Constants.MAIN_MENU_BUTTON_OFFSET_X, 150), statsFont, "highScores");
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(name != null) {
			titleFont.draw(batch, Resources.tr("stats"), Constants.SCOREBOARD_TITLE_OFFSET_X, Constants.SCOREBOARD_TITLE_OFFSET_Y);
			titleFont.draw(batch, name, Constants.SCOREBOARD_TITLE_OFFSET_X, Constants.SCOREBOARD_NAME_OFFSET_Y);
			
			if(score != null) {
				statsFont.draw(batch, Resources.tr("time"), Constants.SCOREBOARD_SCORE_TITLE_OFFSET_X, Constants.SCOREBOARD_TIME_OFFSET_Y);
				statsFont.draw(batch, score.getFormattedTime(), Constants.SCOREBOARD_SCORE_OFFSET_X, Constants.SCOREBOARD_TIME_OFFSET_Y);
				
				statsFont.draw(batch, Resources.tr("shots"), Constants.SCOREBOARD_SCORE_TITLE_OFFSET_X, Constants.SCOREBOARD_SHOTS_OFFSET_Y);
				statsFont.draw(batch, Integer.toString(score.getShots()), Constants.SCOREBOARD_SCORE_OFFSET_X, Constants.SCOREBOARD_SHOTS_OFFSET_Y);
				
				statsFont.draw(batch, Resources.tr("points"), Constants.SCOREBOARD_SCORE_TITLE_OFFSET_X, Constants.SCOREBOARD_POINTS_OFFSET_Y);
				statsFont.draw(batch, Integer.toString(points), Constants.SCOREBOARD_SCORE_OFFSET_X, Constants.SCOREBOARD_POINTS_OFFSET_Y);
				
				statsFont.draw(batch, Resources.tr("ranking"), Constants.SCOREBOARD_SCORE_TITLE_OFFSET_X, Constants.SCOREBOARD_RANKING_OFFSET_Y);
				statsFont.draw(batch, Integer.toString(ranking), Constants.SCOREBOARD_SCORE_OFFSET_X, Constants.SCOREBOARD_RANKING_OFFSET_Y);
			}
			
			highScoresButton.render(batch);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(Mouse.clicked()) {
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			Vector2 mouseLocation = new Vector2(Gdx.input.getX(), yCoord);
			if(highScoresButton.getRect().containsPoint(mouseLocation))
				stateManager.changeState("highScores");
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void init() {
		name = null;
		Gdx.input.getTextInput(inputListener, Resources.tr("typeName"), "", "");
		points = calculatePoints();
	}
	
	public void setScore(Score score) {
		this.score = score;
	}
	
	public void setName(String name) {
		this.name = name;
		PlayerScore playerScore = new PlayerScore(name, points);
		highScoreManager.updateHighScores(playerScore);
		ranking = highScoreManager.getRanking(playerScore) + 1;
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
