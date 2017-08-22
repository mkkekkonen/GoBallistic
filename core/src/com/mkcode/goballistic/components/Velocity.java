package com.mkcode.goballistic.components;

import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Velocity {

	private Vector2 velocity;
	private float mass;
	
	/**
	 * Initialize velocity to 0,0, also initialize mass
	 * @param mass float the mass of the object
	 */
	public Velocity(float mass) {
		this.velocity = new Vector2(); // initialize velocity to 0,0
		this.mass = mass;
	}
	
	/**
	 * Default update method, add gravity to the velocity
	 * @param deltaTime float frame time
	 */
	public void update(float deltaTime) {
		velocity = velocity.add(Constants.GRAVITY); // add gravity to the velocity
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
