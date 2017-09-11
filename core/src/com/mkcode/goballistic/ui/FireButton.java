package com.mkcode.goballistic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Circle;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.util.Constants;

public class FireButton extends AbstractUIElement {

	private Circle circle;
	
	public FireButton() {
		super("button.png");
		this.circle = new Circle(500, MToPx.mToPx(Constants.GAME_AREA_HEIGHT + Constants.CONTROLS_AREA_HEIGHT / 2), 30, false);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		float x = this.circle.getX() - this.circle.getRadius();
		float y = Constants.WND_HEIGHT - (this.circle.getY() + this.circle.getRadius());
		batch.draw(texture, x, y);
	}

	public Circle getCircle() {
		return circle;
	}
}
