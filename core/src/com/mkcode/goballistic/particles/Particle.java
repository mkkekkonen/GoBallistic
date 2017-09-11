package com.mkcode.goballistic.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.gameobjects.AbstractMovingGameObject;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Particle extends AbstractMovingGameObject {

	private Vector2 centerPoint;
	
	private long lifetime, creationTime;
	
	public Particle(float x, float y, float angle, float force, long lifetime) {
		super(x, y, Constants.PARTICLE_DIM, Constants.PARTICLE_DIM, angle, force, 2, "particle.png");
		centerPoint = new Vector2(x, y);
		this.lifetime = lifetime;
		creationTime = System.currentTimeMillis();
	}

	@Override
	public void update(float deltaTime) {
		centerPoint = centerPoint.add(velocity.getVelocity().mul(deltaTime));
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, MToPx.mToPx(centerPoint.getX() - Constants.PARTICLE_DIM / 2), MToPx.mToPx(centerPoint.getY() - Constants.PARTICLE_DIM / 2));
	}
	
	public boolean lifetimeElapsed() {
		return System.currentTimeMillis() - creationTime > lifetime;
	}
}
