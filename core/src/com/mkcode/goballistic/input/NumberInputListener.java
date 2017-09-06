package com.mkcode.goballistic.input;

import com.badlogic.gdx.Input.TextInputListener;
import com.mkcode.goballistic.states.GamePlayingState;

public class NumberInputListener implements TextInputListener {

	private NumberInput parentInput;
	private InputType inputType;
	
	public NumberInputListener(NumberInput parentInput, InputType inputType) {
		this.parentInput = parentInput;
		this.inputType = inputType;
	}
	
	@Override
	public void input(String text) {
		float inputNumeric;
		try {
			inputNumeric = Float.parseFloat(text);
		} catch(NumberFormatException ex) {
			inputNumeric = 0;
		}
		if(this.inputType == InputType.ANGLE) {
			// clamp between 0 and 90
			inputNumeric = Math.max(0, Math.min(90, inputNumeric));
			GamePlayingState.ANGLE_INPUT_SHOWN = false;
		}
		else if(this.inputType == InputType.FORCE) {
			// clamp between 0.1 and 10,000
			inputNumeric = Math.max(0.1f, Math.min(10000, inputNumeric));;
			GamePlayingState.FORCE_INPUT_SHOWN = false;
		}
		this.parentInput.setValue(inputNumeric);
	}

	@Override
	public void canceled() {
		if(this.inputType == InputType.ANGLE)
			GamePlayingState.ANGLE_INPUT_SHOWN = false;
		else if(this.inputType == InputType.FORCE)
			GamePlayingState.FORCE_INPUT_SHOWN = false;
	}
}
