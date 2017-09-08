package com.mkcode.goballistic.input;

import com.badlogic.gdx.Input.TextInputListener;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.states.ScoreboardState;

public class NameInputListener implements TextInputListener {

	private ScoreboardState scoreboard;

	public NameInputListener(ScoreboardState scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	@Override
	public void input(String name) {
		scoreboard.setName(name);
	}

	@Override
	public void canceled() {
		scoreboard.setName(Resources.tr("empty"));
	}
}
