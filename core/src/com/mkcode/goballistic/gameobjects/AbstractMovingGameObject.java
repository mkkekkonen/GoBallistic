package com.mkcode.goballistic.gameobjects;

import com.mkcode.goballistic.components.Velocity;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractMovingGameObject extends AbstractGameObject {

	protected Vector2 location;
	protected Velocity velocity;
	
	protected AbstractMovingGameObject(float x, float y, float mass, String fileName) {
		super(x, y, fileName);
		this.location = new Vector2(x, y);
		this.velocity = new Velocity(mass);
	}

	@Override
	public void update(float deltaTime) {
		this.velocity.update(deltaTime);
		this.location = this.location.add(
				this.velocity.getVelocity().mul(deltaTime)
		);
	}
	
	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}
}
