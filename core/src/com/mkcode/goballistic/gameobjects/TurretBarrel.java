package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mkcode.goballistic.ground.GroundGenerator;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class TurretBarrel extends AbstractGameObject {

	private Vector2 origin;
	
	private float rotation;
	
	public TurretBarrel() {
		super(0, 0, "turret_barrel.png");
	}

	public void init() {
		this.bottomLeft = new Vector2(Constants.TURRET_OFFSET + Constants.TURRET_WIDTH / 2, GroundGenerator.getTurretLevel());
		float textureWidthHalf = this.texture.getWidth() / 2;
		this.bottomLeft.setX(this.bottomLeft.getX() - MToPx.pxToM(textureWidthHalf));
		this.bottomLeft.setY(this.bottomLeft.getY() + MToPx.pxToM(textureWidthHalf));
		this.origin = new Vector2(textureWidthHalf, textureWidthHalf);
		this.rotation = -45;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(
				new TextureRegion(this.texture), 
				MToPx.mToPx(this.bottomLeft.getX()), 
				MToPx.mToPx(this.bottomLeft.getY()), 
				this.origin.getX(), 
				this.origin.getY(), 
				this.texture.getWidth(), 
				this.texture.getHeight(), 
				1, 
				1, 
				this.rotation, 
				false
		);
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	
}
