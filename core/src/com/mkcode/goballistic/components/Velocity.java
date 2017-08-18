package com.mkcode.goballistic.components;

import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Velocity {

	private Vector2 velocity;
	private float mass;
	
	public Velocity(float mass) {
		this.velocity = new Vector2();
		this.mass = mass;
	}
	
	public Velocity(float angle, float force, float mass) {
		this(mass);
		Vector2 initialAcceleration = Vector2.polar(angle, force / mass);
		velocity = velocity.add(initialAcceleration);
	}
	
	public void update(float deltaTime) {
		velocity = velocity.add(Constants.GRAVITY);
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
