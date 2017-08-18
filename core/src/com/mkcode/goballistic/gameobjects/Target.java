package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.util.Constants;

public class Target extends AbstractCollidableGameObject {

	TargetSize targetSize;
	
	public Target(float x, float y, TargetSize targetSize) {
		super(
				x, 
				y, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM, 
				targetSize == TargetSize.SMALL ? Constants.SMALL_TARGET_WEIGHT : Constants.LARGE_TARGET_WEIGHT,
				"target10px.png"
		);
		this.targetSize = targetSize;
	}
	
	@Override
	public void update(float deltaTime) {
//		super.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		if(this.targetSize == TargetSize.SMALL) {
			batch.draw(
					this.texture, 
					MToPx.mToPx(this.location.getX()) - this.rect.getW() / 2, 
					MToPx.mToPx(this.location.getY()) - this.rect.getH() / 2
			);
		}
		else if(this.targetSize == TargetSize.LARGE) {
			batch.draw( // top left
					this.texture, 
					MToPx.mToPx(this.location.getX()) - this.rect.getW() / 2, 
					MToPx.mToPx(this.location.getY()) - this.rect.getH() / 2
			);
			batch.draw( // top right
					this.texture, 
					MToPx.mToPx(this.location.getX()), 
					MToPx.mToPx(this.location.getY()) - this.rect.getH() / 2
			);
			batch.draw( // bottom left
					this.texture, 
					MToPx.mToPx(this.location.getX()) - this.rect.getW() / 2, 
					MToPx.mToPx(this.location.getY())
			);
			batch.draw( // bottom right
					this.texture, 
					MToPx.mToPx(this.location.getX()), 
					MToPx.mToPx(this.location.getY())
			);
		}
	}
}
