package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.components.Velocity;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.math.Circle;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.states.GamePlayingState;
import com.mkcode.goballistic.util.Constants;

public class Bullet extends AbstractMovingGameObject {
	
	Circle circle;
	
	public Bullet(float x, float y, float angle, float force, float mass) {
		super(x, y, Constants.BULLET_DIM, Constants.BULLET_DIM, mass, "bullet.png");
		this.circle = new Circle(x, y, Constants.BULLET_R, true);
//		this.velocity = new Velocity(angle, force, mass);
		this.velocity = new Velocity(mass);
		Vector2 initialAcceleration = Vector2.polar(angle, force / mass);
		velocity.setVelocity(velocity.getVelocity().add(initialAcceleration));
	}

	@Override
	public void update(float deltaTime) {
		this.velocity.update(deltaTime);
		this.circle.setCenterPoint(
				this.circle.getCenterPoint().add(
						this.velocity.getVelocity().mul(deltaTime)
				)
		);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(
				this.texture, 
				MToPx.mToPx(this.circle.getCenterPoint().getX()) - this.texture.getWidth() / 2, 
				MToPx.mToPx(this.circle.getCenterPoint().getY()) + this.texture.getHeight() / 2
		);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		GamePlayingState.FIRING = false;
	}
	
	public Vector2 getLocation() {
		return new Vector2(this.circle.getX(), this.circle.getY());
	}
	
	public void setLocation(Vector2 location) {
		this.circle.setCenterPoint(location);
	}

	public Circle getCircle() {
		return circle;
	}
}
