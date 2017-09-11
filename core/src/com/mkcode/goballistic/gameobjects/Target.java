package com.mkcode.goballistic.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Target extends AbstractMovingGameObject {

	TargetSize targetSize;
	
	public Target(float x, float y, TargetSize targetSize) {
		super(
				x, 
				y, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_WEIGHT : Constants.LARGE_TARGET_WEIGHT,
				0,
				0,				
				"target10px.png"
		);
		this.targetSize = targetSize;
	}
	
	public void update(float deltaTime, Ground ground) {
		List<Integer> collisionColumns = new ArrayList<Integer>();
		if(this.targetSize == TargetSize.SMALL) {
			if(this.rect.getBottomLeft().getX() % 1 == 0) { // target in one/two squares
				collisionColumns.add((int)this.rect.getBottomLeft().getX());
			}
			else { // target in four squares
				collisionColumns.add((int)Math.floor(this.rect.getBottomLeft().getX()));
				collisionColumns.add((int)Math.ceil(this.rect.getBottomLeft().getX()));
			}
		}
		else if(this.targetSize == TargetSize.LARGE) {
			if(this.rect.getBottomLeft().getX() % 1 == 0) { // target in four/six squares
				collisionColumns.add((int)this.rect.getBottomLeft().getX());
				collisionColumns.add((int)this.rect.getBottomLeft().getX() + 1);
			}
			else { // target in nine squares
				collisionColumns.add((int)Math.floor(this.rect.getBottomLeft().getX()));
				collisionColumns.add((int)Math.ceil(this.rect.getBottomLeft().getX()));
				collisionColumns.add((int)Math.ceil(this.rect.getBottomLeft().getX()) + 1);
			}
		}
		
		Integer[][] groundElementMatrix = ground.getGroundElementMatrix();
		int yCoord = calcGroundYCoord(this.rect.getY());
		int smallestY = 100;
		boolean collision = false;
		for(int x : collisionColumns) {
			for(int y = 0; y <= yCoord; y++) {
				if(x >= 0 && x < Constants.GROUND_WIDTH // check that x and y are inside the bounds of the matrix
						&& y >= 0 && y < Constants.MAX_GROUND_HEIGHT
						&& groundElementMatrix[y][x] == 1) {
					collision = true;
					this.velocity.setVelocity(new Vector2());
					if(y < smallestY)
						smallestY = y;
				}
			}
		}
		if(smallestY != 100)
			this.rect.setBottomLeft(new Vector2(this.rect.getX(), calcWorldYCoord(smallestY)));
		
		if(!collision)
			super.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		if(this.targetSize == TargetSize.SMALL) {
			batch.draw(
					this.texture, 
					MToPx.mToPx(this.rect.getX()), 
					MToPx.mToPx(this.rect.getY())
			);
		}
		else if(this.targetSize == TargetSize.LARGE) {
			batch.draw( // top left
					this.texture, 
					MToPx.mToPx(this.rect.getX()), 
					MToPx.mToPx(this.rect.getY() + this.rect.getH() / 2)
			);
			batch.draw( // top right
					this.texture, 
					MToPx.mToPx(this.rect.getX() + this.rect.getW() / 2), 
					MToPx.mToPx(this.rect.getY() + this.rect.getH() / 2)
			);
			batch.draw( // bottom left
					this.texture, 
					MToPx.mToPx(this.rect.getX()), 
					MToPx.mToPx(this.rect.getY())
			);
			batch.draw( // bottom right
					this.texture, 
					MToPx.mToPx(this.rect.getX() + this.rect.getW() / 2), 
					MToPx.mToPx(this.rect.getY())
			);
		}
	}
	
	/**
	 * Converts a world Y coordinate to ground coordinate
	 * @param worldYCoord float world Y coordinate to convert
	 * @return int ground Y coordinate
	 */
	private int calcGroundYCoord(float worldYCoord) {
		return (int)(Constants.MAX_GROUND_HEIGHT - (worldYCoord - 11)); // first subtract controls area height, 
																		// then "reverse" y coordinates direction 
																		// by subtracting from max ground height
	}
	
	private float calcWorldYCoord(int groundYCoord) {
		return (float)(Constants.MAX_GROUND_HEIGHT - groundYCoord + 11);
	}
}
