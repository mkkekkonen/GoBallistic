package com.mkcode.goballistic.ui;

import com.mkcode.goballistic.math.Vector2;

public class ArrowButton extends AbstractRectUIElement {

	public ArrowButton(Vector2 bottomLeft, boolean directionUp) {
		super(bottomLeft, 27, 31, directionUp ? "arrow_up.png" : "arrow_down.png");
	}
}
