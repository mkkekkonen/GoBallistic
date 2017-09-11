package com.mkcode.goballistic.util;

public class Random {

	private static java.util.Random random;
	
	public static java.util.Random getRandom() {
		if(random == null)
			random = new java.util.Random(System.currentTimeMillis());
		return random;
	}
}
