package com.mkcode.mousefix;

import com.badlogic.gdx.Gdx;

public class Mouse {

	private static boolean clickedLastFrame = false;
	private static boolean clickedNow = false;
	private static boolean clickedTapped = false;
	
	public static void update() {
		clickedLastFrame = clickedNow;
		clickedNow = Gdx.input.isTouched();
	}
	
	public static boolean clicked() {
		return clickedNow && !clickedLastFrame;
	}
}
