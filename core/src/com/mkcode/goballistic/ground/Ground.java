package com.mkcode.goballistic.ground;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.gameobjects.GroundElement;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Ground {

	private Integer[][] groundElementMatrix;
	private List<Integer> groundElements;
	private GroundElement groundElementFlyweight;

	public Ground() {
		groundElementMatrix = new Integer[Constants.GAME_AREA_HEIGHT][Constants.GROUND_WIDTH];
		groundElements = new ArrayList<Integer>();
		groundElementFlyweight = new GroundElement();
	}
	
	public void render(SpriteBatch batch) {
//		for(int w = 0; w < Constants.GROUND_WIDTH; w++) {
//			for(int h = 0; h < Constants.MAX_GROUND_HEIGHT; h++) {
//				
//				int distance = Constants.MAX_GROUND_HEIGHT - groundElements.get(w);
//				
//				if(h >= distance) {
//					groundElementFlyweight.setBottomLeft(
//							new Vector2(
//									w,
//									Constants.WND_ELE_HEIGHT - h - Constants.GROUND_TOP_OFFSET
//							)
//					);
//					groundElementFlyweight.render(batch);
//				}
//				
////				if(groundElements.get(w) < h) {
////					groundElementFlyweight.setBottomLeft(
////						new Vector2(
////							w * Constants.GROUND_ELEMENT_WIDTH, 
////							(Constants.WND_ELE_HEIGHT - h) * Constants.GROUND_ELEMENT_WIDTH - Constants.GROUND_TOP_OFFSET
////						)
////					);
////					groundElementFlyweight.render(batch);
////				}
//			}
//		}
		
		for(int y = 0; y < Constants.MAX_GROUND_HEIGHT; y++) {
			for(int x = 0; x < Constants.GROUND_WIDTH; x++) {
				if(groundElementMatrix[y][x] == 1) {
					groundElementFlyweight.setBottomLeft(
							new Vector2(
									x,
									Constants.GAME_AREA_HEIGHT - y
							)
					);
					groundElementFlyweight.render(batch);
				}
			}
		}
	}
	
	public void dispose() {
		this.groundElementFlyweight.dispose();
	}
	
	public Integer[][] getGroundElementMatrix() {
		return this.groundElementMatrix;
	}
	
	public List<Integer> getGroundElements() {
		return this.groundElements;
	}
}
