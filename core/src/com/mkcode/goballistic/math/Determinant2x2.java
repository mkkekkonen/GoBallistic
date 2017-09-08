package com.mkcode.goballistic.math;

/**
 * 
 * @author mkkek
 * 
 * For example
 * |a b|
 * |c d|
 * = a*d - b*c
 */
public class Determinant2x2 {

	Object topLeft, topRight, bottomLeft, bottomRight;
	
	public Determinant2x2(Object topLeft, Object topRight, Object bottomLeft, Object bottomRight) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}
	
	public float calculate() {
		float fTopLeft = getFloatFromDetElement(topLeft),
				fTopRight = getFloatFromDetElement(topRight),
				fBottomLeft = getFloatFromDetElement(bottomLeft),
				fBottomRight = getFloatFromDetElement(bottomRight);
		
		return fTopLeft * fBottomRight - fTopRight * fBottomLeft;
	}
	
	private float getFloatFromDetElement(Object element) {
		float value = 0;
		if(element instanceof Determinant2x2)
			value = ((Determinant2x2)element).calculate();
		else if(element instanceof Float)
			value = (Float)element;
		return value;
	}
}
