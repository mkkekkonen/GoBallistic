package com.mkcode.goballistic.math;

import com.mkcode.goballistic.util.Constants;

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

	public float cross(Vector2 v) {
		return x * v.getY() - v.getX() * y;
	}
	
	public static Vector2 polar(float angle, float len) {
		return new Vector2(
				(float)(len * Math.cos(Math.toRadians(angle))), 
				(float)(len * Math.sin(Math.toRadians(angle)))
		);
	}
	
	/**
	 * Assumes we're dealing with a point, not a vector.
	 * Determines if the point lies on a line.
	 * Source: https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/
	 * @param line Line
	 * @return boolean, if true: point lies on the line
	 */
	public boolean isOnLine(Line line) {
		return Math.abs(getLineThroughOriginCrossProduct(line)) < Constants.EPSILON; // check whether the absolute value of the cross product is close to zero
	}
	
	/**
	 * Assumes we're dealing with a point, not a vector.
	 * Determines if the point is on the right side of the line.
	 * Source: https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/
	 * @param line
	 * @return
	 */
	public boolean isRightOfLine(Line line) {
		return getLineThroughOriginCrossProduct(line) < 0;
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
	
	/**
	 * Move line to go through origin, move the point the same amount,
	 * and calculate the cross product
	 * Source: https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/
	 * @param line Line
	 * @return float the cross product of the adjusted line and point
	 */
	private float getLineThroughOriginCrossProduct(Line line) {
		float thisY = Constants.WND_HEIGHT - y,
				lineY1 = Constants.WND_HEIGHT - line.getY1(),
				lineY2 = Constants.WND_HEIGHT - line.getY2();
		// move line to origin
		Line lineThroughOrigin = new Line(
				0,
				0,
				line.getX2() - line.getX1(),
				lineY2 - lineY1
		);
		Vector2 adjustedPoint = new Vector2(x - line.getX1(), thisY - lineY1); // move point relative to the adjusted line
		Vector2[] lineEndPoints = lineThroughOrigin.getEndPoints();
		float cross = lineEndPoints[1].cross(adjustedPoint); 	// cross product of the second line endpoint and 
																// the point in question adjusted relative to it
		return cross;
	}
}
