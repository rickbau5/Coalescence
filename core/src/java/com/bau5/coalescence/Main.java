package com.bau5.coalescence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.bau5.coalescence.screens.BaseScreen;
import com.bau5.coalescence.screens.GameScreen;


/**
 * Created by Rick on 3/30/2016.
 */
public class Main extends Game {

    @Override
    public void create () {
        switchToScreen(new GameScreen());
    }

    public void switchToScreen(BaseScreen screen) {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        setScreen(screen);
    }
}