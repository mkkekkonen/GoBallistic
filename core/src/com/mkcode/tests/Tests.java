package com.mkcode.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mkcode.goballistic.math.Line;
import com.mkcode.goballistic.math.Rect;

public class Tests {

	@Test
	public void lineShouldIntersectRect() {
		Rect rect = new Rect(3, 2, 2, 2);
		Line line = new Line(4, 3, 4.5f, 3.5f);
		
		assertTrue("Line should intersect Rect", line.intersectsRect(rect));
	}

}
