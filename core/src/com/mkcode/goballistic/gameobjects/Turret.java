package com.mkcode.goballistic.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.ground.GroundGenerator;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class Turret extends AbstractGameObject {

	private TurretBarrel barrel;
	
	private float force;
	
	public Turret() {
		super(0, 0, "turret_base.png");
		barrel = new TurretBarrel();
	}
	
	public void init() {
		this.bottomLeft = new Vector2(Constants.TURRET_OFFSET, GroundGenerator.getTurretLevel());
		this.barrel.init();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		this.barrel.render(batch);
		super.render(batch);
	}
	
	public void setAngle(float angle) {
		this.barrel.setRotation(-(90-angle));
	}

	public float getForce() {
		return force;
	}

	public void setForce(float force) {
		this.force = force;
	}
}
