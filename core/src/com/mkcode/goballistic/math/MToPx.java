package com.mkcode.goballistic.math;

import com.mkcode.goballistic.util.Constants;

public class MToPx {

	/**
	 * Converts meters to pixels.
	 * @param m int meters
	 * @return int pixels
	 */
	public static int mToPx(int m) {
		return m * Constants.PIXELS_PER_METER;
	}
	
	/**
	 * Converts meters to pixels.
	 * @param m float meters
	 * @return int pixels
	 */
	public static int mToPx(float m) {
		return (int)(m * Constants.PIXELS_PER_METER);
	}
	
	/**
	 * Converts the meters of a Vector2 to pixels.
	 * @param v Vector2 the vector
	 * @return Vector2 the new vector
	 */
	public static Vector2 mToPx(Vector2 v) {
		return new Vector2(mToPx(v.getX()), mToPx(v.getY()));
	}
	
	/**
	 * Converts pixels to meters.
	 * @param px float pixels
	 * @return float meters
	 */
	public static float pxToM(float px) {
		return px / Constants.PIXELS_PER_METER;
	}
}
