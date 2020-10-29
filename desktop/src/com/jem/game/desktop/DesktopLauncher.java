package com.jem.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import levels.levelGenerator;


public class DesktopLauncher {
	public static void main (String[] arg) {
		final String LEVEL1= "game_background_1.png";
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;
		config.fullscreen = false;
		new LwjglApplication(new levelGenerator(LEVEL1), config);
	}
}
