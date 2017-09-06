package com.mkcode.goballistic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.gameobjects.Turret;

public class NumberInput extends AbstractInput {

	private float value;
	private NumberInputListener listener;
	private InputType inputType;
	private Turret turret;
	
	public NumberInput(Turret turret, BitmapFont font, int x, int y, String title, InputType inputType) {
		super(font, "numberinput.png", x, y, title);
		this.turret = turret;
		this.inputType = inputType;
		if(this.inputType == InputType.ANGLE)
			this.value = 45;
		else
			this.value = 500;
		this.listener = new NumberInputListener(this, inputType);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		this.font.draw(batch, Float.toString(value), rect.getX() + marginX, rect.getY() + marginY);
	}
	
	public void getInput() {
		Gdx.input.getTextInput(listener, title, Float.toString(Math.round(value * 100f) / 100f), "");
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
		if(this.inputType == InputType.ANGLE)
			this.turret.setAngle(value);
		else if(this.inputType == InputType.FORCE)
			this.turret.setForce(value);
	}
}
