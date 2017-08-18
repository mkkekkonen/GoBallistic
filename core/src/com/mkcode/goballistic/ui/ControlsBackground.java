package com.mkcode.goballistic.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Vector2;

public class ControlsBackground extends AbstractUIElement {
	
	private Vector2 bottomLeft;
	
	public ControlsBackground(int x, int y) {
		super("controls_bg.png");
		this.bottomLeft = new Vector2(x, y);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.texture, this.bottomLeft.getX(), this.bottomLeft.getY());
	}
}
