package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.components.Velocity;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractMovingGameObject extends AbstractCollidableGameObject {

	protected Velocity velocity;
	
	protected AbstractMovingGameObject(float x, float y, float width, float height, float angle, float force, float mass, String fileName) {
		super(x, y, width, height, fileName);
		this.velocity = new Velocity(mass);
		Vector2 initialAcceleration;
		if(!(this instanceof Target)) { 
			initialAcceleration = Vector2.polar(angle, force / mass);
			velocity.setVelocity(velocity.getVelocity().add(initialAcceleration));
		}
	}

	@Override
	public void update(float deltaTime) {
		this.velocity.update(deltaTime);
		this.rect.setBottomLeft(
				this.rect.getBottomLeft().add(
						this.velocity.getVelocity().mul(deltaTime)
				)
		);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, rect.getX(), rect.getY());
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}
}
