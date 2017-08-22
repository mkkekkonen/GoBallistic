package com.mkcode.goballistic.gameobjects;

import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;

public class AbstractCollidableGameObject extends AbstractGameObject {

	protected Rect rect;

	protected AbstractCollidableGameObject(float x, float y, float width, float height, String fileName) {
		super(x, y, fileName);
		this.rect = new Rect(new Vector2(x, y), width, height);
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}
}
