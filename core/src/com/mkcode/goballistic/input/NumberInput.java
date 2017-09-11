package com.mkcode.goballistic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.gameobjects.Turret;
import com.mkcode.goballistic.util.Constants;

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
			value = Constants.INPUT_ANGLE_DEFAULT_VALUE;
		else // force
			value = Constants.INPUT_FORCE_DEFAULT_VALUE;
		listener = new NumberInputListener(this, inputType);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		font.draw(batch, Float.toString(value), rect.getX() + marginX, rect.getY() + marginY);
	}
	
	public void getInput() {
		Gdx.input.getTextInput(listener, title, Float.toString(Math.round(value * 100f) / 100f), "");
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
		if(inputType == InputType.ANGLE)
			turret.setAngle(value);
		else if(inputType == InputType.FORCE)
			turret.setForce(value);
	}
	
	public void increaseValue(float delta) {
		setValue(value + delta);
	}
	
	public void decreaseValue(float delta) {
		setValue(value - delta);
	}
}
