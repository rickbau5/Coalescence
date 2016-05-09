package com.bau5.coalescence.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.Main;
import com.bau5.coalescence.SoundManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.sizeX;
        config.height = Constants.sizeY;

        new LwjglApplication(new Main(), config);
    }
}
