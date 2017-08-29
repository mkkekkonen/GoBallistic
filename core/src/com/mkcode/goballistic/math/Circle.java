package com.mkcode.goballistic.math;

public class Circle {

	private Vector2 centerPoint;
	
	private float radius;
	private boolean WORLD_COORDS;
	
	public Circle(float x, float y, float radius, boolean worldCoords) {
		this.WORLD_COORDS = worldCoords;
		this.centerPoint = new Vector2(x, y);
		this.radius = radius;
	}
	
	public boolean containsPoint(Vector2 point) {
		// (x_p - x_c)^2 + (y_p - y_c)^2 < r^2
		return Math.pow((point.getX() - centerPoint.getX()), 2) + Math.pow((point.getY() - centerPoint.getY()), 2) < Math.pow(radius, 2);
	}
	
	public boolean containsPoint(float x, float y) {
		// (x_p - x_c)^2 + (y_p - y_c)^2 < r^2
		return Math.pow((x - centerPoint.getX()), 2) + Math.pow((y - centerPoint.getY()), 2) < Math.pow(radius, 2);
	}
	
	/**
	 * See http://mathworld.wolfram.com/Circle-LineIntersection.html
	 * 
	 * @param x1 float line first end point x coordinate
	 * @param y1 float line first end point y coordinate
	 * @param x2 float line second end point x coordinate
	 * @param y2 float line second end point y coordinate 
	 * @return boolean intersection
	 */
	public boolean lineIntersects(float x1, float y1, float x2, float y2) {
		
		// circle at the origin
		// - set line relative to it
		if(WORLD_COORDS) {
			x1 -= this.centerPoint.getX();
			y1 -= this.centerPoint.getY();
			x2 -= this.centerPoint.getX();
			y2 -= this.centerPoint.getY();
		}
		else {
			x1 -= MToPx.pxToM(this.centerPoint.getX());
			y1 -= MToPx.pxToM(this.centerPoint.getY());
			x2 -= MToPx.pxToM(this.centerPoint.getX());
			y2 -= MToPx.pxToM(this.centerPoint.getY());
		}
		
		float dx = x2 - x1,
				dy = y2 - y1,
				dr = (float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)),
				D = x1 * y2 - x2 * y1;
		
		float delta = (float)(Math.pow(this.radius, 2) * Math.pow(dr, 2) - Math.pow(D, 2));
		
		return delta >= 0;
	}

	/**
	 * See http://mathworld.wolfram.com/Circle-LineIntersection.html
	 * 
	 * @param line Line
	 * @return boolean intersection
	 */
	public boolean lineIntersects(Line line) {

		// circle at the origin
		// - set line relative to it
		
		if(WORLD_COORDS) {
			line.setX1(line.getX1() - this.centerPoint.getX());
			line.setY1(line.getY1() - this.centerPoint.getY());
			line.setX2(line.getX2() - this.centerPoint.getX());
			line.setY2(line.getY2() - this.centerPoint.getY());
		}
		else {
			line.setX1(line.getX1() - MToPx.pxToM(this.centerPoint.getX()));
			line.setY1(line.getY1() - MToPx.pxToM(this.centerPoint.getY()));
			line.setX2(line.getX2() - MToPx.pxToM(this.centerPoint.getX()));
			line.setY2(line.getY2() - MToPx.pxToM(this.centerPoint.getY()));
		}
		
		float dx = line.getX2() - line.getX1(),
				dy = line.getY2() - line.getY1(),
				dr = (float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)),
				D = line.getX1() * line.getY2() - line.getX2() * line.getY1();
		
		double radiusPow = Math.pow(this.radius, 2),
				drPow = Math.pow(dr, 2),
				DPow = Math.pow(D, 2);
		
		double delta = radiusPow * drPow - DPow;
		
		System.out.println("\ndelta: " + delta);
		
		return delta >= 0;
	}
	
	public float getX() {
		return centerPoint.getX();
	}
	
	public float getY() {
		return centerPoint.getY();
	}
	
	public Vector2 getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Vector2 centerPoint) {
		this.centerPoint = centerPoint;
	}

	public void setCenterPoint(float x, float y) {
		this.centerPoint = new Vector2(x, y);
	}
	
	public float getRadius() {
		return radius;
	}
	
	@Override
	public String toString() {
		return "Circle ~ X: " + this.centerPoint.getX() + " Y: " + this.centerPoint.getY() + " r: " + this.radius;
	}
}
