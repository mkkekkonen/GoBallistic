package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractCollidableGameObject extends AbstractGameObject {

	protected Rect rect;

	protected AbstractCollidableGameObject(float x, float y, float width, float height, String fileName) {
		super(x, y, fileName);
		this.rect = new Rect(new Vector2(x, y), width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.texture, MToPx.mToPx(this.rect.getX()), MToPx.mToPx(this.rect.getY()));
	}
	
	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}
	
	@Override
	public Vector2 getBottomLeft() {
		return this.rect.getBottomLeft();
	}
	
	@Override
	public void setBottomLeft(Vector2 bottomLeft) {
		this.rect.setBottomLeft(bottomLeft);
	}
}
