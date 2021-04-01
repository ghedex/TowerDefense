package com.jem.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import levels.LevelOne;
import levels.levelGenerator;
import levels.menu.mainMenu;
import levels.menu.mainMenuGenerator;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;
		config.fullscreen = false;
		config.resizable = false;
		config.vSyncEnabled = true;
		config.foregroundFPS = 75;
		config.title = "JEM, MEM, CAN'T PROGRAM";
		new LwjglApplication(new mainMenuGenerator(), config);
	}
}
