package com.mkcode.goballistic.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractRectUIElement extends AbstractUIElement {

	protected Rect rect;
	
	protected AbstractRectUIElement(Vector2 bottomLeft, float width, float height, String fileName) {
		super(fileName);
		rect = new Rect(bottomLeft, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, rect.getX(), rect.getY());
	}
	
	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}
}
