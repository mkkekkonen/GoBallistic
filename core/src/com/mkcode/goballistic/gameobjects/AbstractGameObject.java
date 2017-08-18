package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractGameObject {

	protected Vector2 
		bottomLeft,
		bottomLeftPixels;
	protected Texture texture;
	
	protected AbstractGameObject(float x, float y, String fileName) {
		texture = new Texture(Gdx.files.internal(fileName));
		bottomLeft = new Vector2(x, y);
		bottomLeftPixels = MToPx.mToPx(bottomLeft);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, bottomLeftPixels.getX(), bottomLeftPixels.getY());
	}
	
	public void update(float deltaTime) {
		
	}

	public void dispose() {
		texture.dispose();
	}
	
	public Vector2 getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Vector2 bottomLeft) {
		this.bottomLeft = bottomLeft;
		this.bottomLeftPixels = MToPx.mToPx(bottomLeft);
	}
}
