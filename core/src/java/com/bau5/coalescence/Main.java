package com.bau5.coalescence;

import com.badlogic.gdx.Game;
import com.bau5.coalescence.ui.screens.BaseScreen;
import com.bau5.coalescence.ui.screens.MenuScreen;


/**
 * Created by Rick on 3/30/2016.
 */
public class Main extends Game {
    @Override
    public void create () {
        switchToScreen(new MenuScreen(this));
    }

    public void switchToScreen(BaseScreen screen) {
        if (getScreen() != null) {
            getScreen().dispose();
        }

        setScreen(screen);
    }
}