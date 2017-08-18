package com.mkcode.goballistic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mkcode.goballistic.GoBallistic;
import com.mkcode.goballistic.util.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.WND_WIDTH;
		config.height = Constants.WND_HEIGHT;
		new LwjglApplication(new GoBallistic(), config);
	}
}
