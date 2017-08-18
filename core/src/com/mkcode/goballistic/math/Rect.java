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
		if(point.getX() >= bottomLeft.getX() && point.getX() <= bottomLeft.getX() + w
				&& point.getY() >= bottomLeft.getY() && point.getY() <= bottomLeft.getY() + h)
			return true;
		return false;
	}
	
	public boolean containsPoint(float x, float y) {
		if(x >= bottomLeft.getX() && x <= bottomLeft.getX() + w
				&& y >= bottomLeft.getY() && y <= bottomLeft.getY() + h)
			return true;
		return false;
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
