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
	 * Determines if an infinite line intersects the circle.
	 * See http://mathworld.wolfram.com/Circle-LineIntersection.html
	 * 
	 * @param line Line
	 * @return boolean intersection
	 */
	public boolean intersectsLine(Line line) {

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
				D = new Determinant2x2(
						line.getX1(), line.getX2(), 
						line.getY1(), line.getY2()
				).calculate();
		
		float radiusPow = (float)Math.pow(this.radius, 2),
				drPow = (float)Math.pow(dr, 2),
				DPow = (float)Math.pow(D, 2);
		
		float delta = (float)(radiusPow * drPow - DPow);
		
		return delta >= 0;
	}
	
	/**
	 * See http://mathworld.wolfram.com/Circle-LineIntersection.html
	 * 
	 * @param line Line the line in question
	 * @return Vector2[] a 2-element array containing the intersection points
	 */
	public Vector2[] getLineIntersectionPoints(Line line) {
		
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
				D = new Determinant2x2(
						line.getX1(), line.getX2(), 
						line.getY1(), line.getY2()
				).calculate();
		
		float radiusPow = (float)Math.pow(this.radius, 2),
				drPow = (float)Math.pow(dr, 2),
				DPow = (float)Math.pow(D, 2);
		
		float delta = (float)(radiusPow * drPow - DPow);
		
		if(delta < 0) // no intersection
			return null;
		
		float resX1 = (float)((D * dy + (dy < 0 ? -1 : 1) * dx * Math.sqrt(delta)) / drPow);
		float resY1 = (float)((-D * dx + Math.abs(dy) * Math.sqrt(delta)) / drPow);
		
		if(delta == 0) // one intersection point, tangent
			return new Vector2[] {
					// move intersection point from the origin to its actual place
					new Vector2(resX1 + this.centerPoint.getX(), resY1 + this.centerPoint.getY()) 
			};
		else { // two intersection points
			float resX2 = (float)((D * dy - (dy < 0 ? -1 : 1) * dx * Math.sqrt(delta)) / drPow);
			float resY2 = (float)((-D * dx - Math.abs(dy) * Math.sqrt(delta)) / drPow);
			return new Vector2[] {
					// move intersection points from the origin to their actual place
					new Vector2(resX1 + this.centerPoint.getX(), resY1 + this.centerPoint.getY()),
					new Vector2(resX2 + this.centerPoint.getX(), resY2 + this.centerPoint.getY())
			};
		}
	}
	
	/**
	 * Determines whether the circle intersects with the infinite
	 * lines bounding the rectangle.
	 * @param rect Rect the rectangle
	 * @return boolean intersects
	 */
	public boolean intersectsRectLines(Rect rect) {
		Line[] rectBounds = rect.getBounds();
		for(Line line : rectBounds) {
			if(this.intersectsLine(line))
				return true;
		}
		return false;
	}
	
	/**
	 * Determines whether the circle intersects with the actual
	 * rectangle, as determined by the intersection points
	 * lying on rect bounds.
	 * @param rect Rect the rectangle
	 * @return boolean intersection points lying on rect bounds
	 */
	public boolean intersectsRect(Rect rect) {
		boolean intersects = false;
		Line[] rectBounds = rect.getBounds();
		for(Line line : rectBounds) {
			Vector2[] intersectionPoints = this.getLineIntersectionPoints(line);
			if(intersectionPoints != null) {
				int i = 1;
				for(Vector2 point : intersectionPoints) {
					if(point.getX() >= rect.getX() 
							&& point.getX() <= rect.getX() + rect.getW()
							&& point.getY() >= rect.getY()
							&& point.getY() <= rect.getY() + rect.getH()) {
						intersects = true;
						break;
					}
					i++;
				}
			}
			else
				intersects = false; // array is null - no intersection found
		}
		return intersects;
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
