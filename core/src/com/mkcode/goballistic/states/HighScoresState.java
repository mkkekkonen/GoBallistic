package com.mkcode.goballistic.states;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.HighScoreManager;
import com.mkcode.goballistic.score.PlayerScore;
import com.mkcode.goballistic.util.Constants;

public class HighScoresState extends AbstractState {

	private BitmapFont titleFont, scoreFont;
	private HighScoreManager highScoreManager;
	
	public HighScoresState(StateManager stateManager, FontManager fontManager, HighScoreManager highScoreManager) {
		super(stateManager, fontManager);
		displayExitButton = true;
		titleFont = fontManager.getFont("titleFont");
		scoreFont = fontManager.getFont("font30");
		this.highScoreManager = highScoreManager;
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		titleFont.draw(batch, Resources.tr("highScores"), Constants.SCOREBOARD_TITLE_OFFSET_X, Constants.SCOREBOARD_TITLE_OFFSET_Y);
		List<PlayerScore> highScoreList = highScoreManager.getHighScoreList();
		for(int i = highScoreList.size() - 1; i >= 0; i--) {
			// print sorted high scores in descending order
			int yCoord = Constants.HIGH_SCORES_START_Y - (highScoreList.size() - i - 1) * (Constants.HIGH_SCORES_FONT_HEIGHT + Constants.HIGH_SCORES_ROW_MARGIN);
			if(highScoreList.get(i).getName() != null)
				scoreFont.draw(
						batch, 
						highScoreList.get(i).getName(), 
						Constants.SCOREBOARD_SCORE_TITLE_OFFSET_X, 
						yCoord
				);
			scoreFont.draw(
					batch, 
					Integer.toString(highScoreList.get(i).getScore()), 
					Constants.HIGH_SCORES_POINTS_OFFSET_X, 
					yCoord
			);
		}
	}
}
