package com.mkcode.goballistic.math;

public class Rect {

	private Vector2 bottomLeft;
	private float w, h;
	
	public Rect(Vector2 bottomLeft, float w, float h) {
		this.bottomLeft = bottomLeft;
		this.w = w;
		this.h = h;
	}
	
	public Rect(float x, float y, float w, float h) {
		this.bottomLeft = new Vector2(x, y);
		this.w = w;
		this.h = h;
	}
	
	public boolean containsPoint(Vector2 point) {
		if(point.getX() >= this.getX() && point.getX() <= this.getX() + w
				&& point.getY() >= this.getY() && point.getY() <= this.getY() + h)
			return true;
		return false;
	}
	
	public boolean containsPoint(float x, float y) {
		if(x >= this.getX() && x <= this.getX() + w
				&& y >= this.getY() && y <= this.getY() + h)
			return true;
		return false;
	}

	public boolean intersectsRect(Rect rect) {
		return this.getX() <= rect.getX() + rect.getW()
				&& this.getX() + this.getW() >= rect.getX()
				&& this.getY() <= rect.getY() + rect.getH()
				&& this.getY() + this.getH() >= rect.getY();
	}
	
	public Line[] getBounds() {
		return new Line[] {
				new Line( // top
						this.bottomLeft.getX(), 
						this.bottomLeft.getY() + this.h,
						this.bottomLeft.getX() + this.w,
						this.bottomLeft.getY() + this.h
				),
				new Line( // right
						this.bottomLeft.getX() + this.w,
						this.bottomLeft.getY() + this.h,
						this.bottomLeft.getX() + this.w,
						this.bottomLeft.getY()
				),
				new Line( // bottom
						this.bottomLeft.getX(),
						this.bottomLeft.getY(),
						this.bottomLeft.getX() + this.w,
						this.bottomLeft.getY()
				),
				new Line( // left
						this.bottomLeft.getX(),
						this.bottomLeft.getY() + this.h,
						this.bottomLeft.getX(),
						this.bottomLeft.getY()
				)
		};
	}
	
	@Override
	public String toString() {
		return "Rect ~ X: " + this.bottomLeft.getX() + " Y: " + this.bottomLeft.getY() +
				" W: " + this.w + " H: " + this.h;
	}
	
	public Vector2 getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Vector2 bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public float getX() {
		return this.bottomLeft.getX();
	}
	
	public float getY() {
		return this.bottomLeft.getY();
	}
	
	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}
	
	
}
