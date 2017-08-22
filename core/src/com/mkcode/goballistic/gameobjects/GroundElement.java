package com.mkcode.goballistic.gameobjects;

import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class GroundElement extends AbstractCollidableGameObject {
	
	public GroundElement() {
		this(0, 0);
	}
	
	public GroundElement(float x, float y) {
		super(x, y, Constants.GROUND_ELEMENT_WIDTH, Constants.GROUND_ELEMENT_WIDTH, "ground.png");
	}
	
	@Override
	public void setBottomLeft(Vector2 bottomLeft) {
		super.setBottomLeft(bottomLeft);
		
	}
}
