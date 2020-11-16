package com.jem.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import levels.LevelOne;


public class DesktopLauncher {
	public static void main (String[] arg) {
		String level= "game_background_1.png";
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new LevelOne(level), config);
	}
}
