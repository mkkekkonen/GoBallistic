package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.components.Velocity;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.math.Circle;
import com.mkcode.goballistic.math.Line;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.states.GamePlayingState;
import com.mkcode.goballistic.util.Constants;

public class Bullet extends AbstractMovingGameObject {
	
	Circle circle;
	Vector2 previousLocation;
	
	public Bullet(float x, float y, float angle, float force, float mass) {
		super(x, y, Constants.BULLET_DIM, Constants.BULLET_DIM, angle, force, mass, "bullet.png");
		circle = new Circle(x, y, Constants.BULLET_R, true);
		previousLocation = new Vector2();
	}

	@Override
	public void update(float deltaTime) {
		velocity.update(deltaTime);
		previousLocation = circle.getCenterPoint();
		circle.setCenterPoint(
				circle.getCenterPoint().add(
						velocity.getVelocity().mul(deltaTime)
				)
		);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(
				texture, 
				MToPx.mToPx(circle.getCenterPoint().getX()) - this.texture.getWidth() / 2, 
				MToPx.mToPx(circle.getCenterPoint().getY()) - this.texture.getHeight() / 2
		);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		GamePlayingState.FIRING = false;
	}
	
	public Line getLineFromPreviousLocation() {
		return new Line(
				previousLocation.getX(),
				previousLocation.getY(),
				getLocation().getX(),
				getLocation().getY()
		);
	}
	
	public Vector2 getLocation() {
		return new Vector2(circle.getX(), circle.getY());
	}
	
	public void setLocation(Vector2 location) {
		circle.setCenterPoint(location);
	}

	public Circle getCircle() {
		return circle;
	}
	
	public Vector2 getPreviousLocation() {
		return previousLocation;
	}
}
