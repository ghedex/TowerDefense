package com.jem.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import levels.LevelOne;
import levels.levelGenerator;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;
		config.fullscreen = false;
		config.resizable = false;
		config.vSyncEnabled = true;
		config.foregroundFPS = 75;
		new LwjglApplication(new levelGenerator(), config);
	}
}
