package com.mkcode.goballistic.math;

public class Vector2 {

	private float x, y, w;
	
	public static final Vector2 
		UP = new Vector2(0, 1),
		DOWN = new Vector2(0, -1),
		LEFT = new Vector2(-1, 0),
		RIGHT = new Vector2(1, 0);
	
	public Vector2() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.w = 1.0f;
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
		this.w = 1.0f;
	}
	
	public Vector2 add(Vector2 v) {
		return new Vector2(
				this.x + v.x,
				this.y + v.y
		);
	}
	
	public Vector2 sub(Vector2 v) {
		return new Vector2(
				this.x - v.x,
				this.y - v.y
		);
	}
	
	public Vector2 mul(float scalar) {
		return new Vector2(
				this.x * scalar,
				this.y * scalar
		);
	}
	
	public Vector2 div(float scalar) {
		return new Vector2(
				this.x / scalar,
				this.y / scalar
		);
	}

	public static Vector2 polar(float angle, float len) {
		return new Vector2(
				(float)(len * Math.cos(Math.toRadians(angle))), 
				(float)(len * Math.sin(Math.toRadians(angle)))
		);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Vector2 ~ x: " + x + ", y: " + y;
	}
}
