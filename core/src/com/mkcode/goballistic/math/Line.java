package com.mkcode.goballistic.math;

public class Line {

	private float x1, y1, x2, y2;
	
	public Line(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public Vector2[] getEndPoints() {
		return new Vector2[] {
				new Vector2(x1, y1),
				new Vector2(x2, y2)
		};
	}
	
	public Rect getBoundingBox() {
		float bottomLeftX = Math.min(x1, x2);
		float bottomLeftY = Math.min(y1, y2);
		float w = Math.max(x1, x2) - bottomLeftX;
		float h = Math.max(y1, y2) - bottomLeftY;
		return new Rect(bottomLeftX, bottomLeftY, w, h);
	}
	
	/**
	 * Source: https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/
	 * @param line Line
	 * @return boolean lines touch or intersect
	 */
	public boolean touchesOrCrossesLine(Line line) {
		Vector2[] otherLineEndPoints = line.getEndPoints();
		return otherLineEndPoints[0].isOnLine(this) // the first end of the other line segment touches this line 
				|| otherLineEndPoints[1].isOnLine(this) // the second end of the other line segment touches this line
				// the endpoints of the other line are on different sides of this line
				|| (otherLineEndPoints[0].isRightOfLine(this) != otherLineEndPoints[1].isRightOfLine(line)); 
	}
	
	/**
	 * Source: https://martin-thoma.com/how-to-check-if-two-line-segments-intersect/
	 * @param line Line
	 * @return boolean line segments intersect
	 */
	public boolean intersectsLineSegment(Line line) {
		Rect thisBoundingBox = getBoundingBox(),
				lineBoundingBox = line.getBoundingBox();
		boolean boxesIntersect = getBoundingBox().intersectsRect(line.getBoundingBox()),
				thisTouchesOrCrosses = touchesOrCrossesLine(line),
				lineTouchesOrCrosses = line.touchesOrCrossesLine(this);
		return boxesIntersect
				&& thisTouchesOrCrosses
				&& lineTouchesOrCrosses;
	}
	
	public boolean intersectsRect(Rect rect) {
		Line[] rectBounds = rect.getBounds();
		for(Line line : rectBounds) {
			boolean thisIntersectsLineSegment = intersectsLineSegment(line); 
			if(thisIntersectsLineSegment)
				return true;
		}
		// is the line inside of the rectangle
		if(x1 >= rect.getX() && x2 >= rect.getX()
				&& x1 <= rect.getX() + rect.getW() && x2 <= rect.getX() + rect.getW()
				&& y1 >= rect.getY() && y2 >= rect.getY()
				&& y1 <= rect.getY() + rect.getH() && y2 <= rect.getY() + rect.getH())
			return true;
		return false;
	}
	
	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}
	
	@Override
	public String toString() {
		return "Line ~ X1: " + this.x1 + " Y1: " + this.y1 + " X2: " + this.x2 + " Y2: " + this.y2;
	}
}
