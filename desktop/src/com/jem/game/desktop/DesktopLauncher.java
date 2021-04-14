package com.jem.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import MainRef.TowerDefense;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;
		config.fullscreen = false;
		config.resizable = false;
		config.vSyncEnabled = true;
		config.foregroundFPS = 60;
		config.title = "Quick, take the pick and run a klick before I show you this nice trick with my brick.";
		new LwjglApplication(new TowerDefense(), config);
	}
}
