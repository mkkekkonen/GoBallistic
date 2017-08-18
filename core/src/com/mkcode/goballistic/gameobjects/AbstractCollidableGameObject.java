package com.mkcode.goballistic.gameobjects;

import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class AbstractCollidableGameObject extends AbstractMovingGameObject {

	protected Rect rect;
	
	protected AbstractCollidableGameObject(float x, float y, float width, float height, float mass, String fileName) {
		super(x, y, mass, fileName);
		this.rect = new Rect(x, y, width, height);
	}

	@Override
	public void setBottomLeft(Vector2 bottomLeft) {
		super.setBottomLeft(bottomLeft);
		this.rect = new Rect(bottomLeft.getX(), bottomLeft.getY(), this.rect.getW(), this.rect.getH());
	}
	
	public Rect getRect() {
		return this.rect;
	}
}
