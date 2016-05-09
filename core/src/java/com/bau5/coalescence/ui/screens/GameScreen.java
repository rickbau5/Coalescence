package com.bau5.coalescence.ui.screens;

import com.bau5.coalescence.GameStage;
import com.bau5.coalescence.Main;
import com.bau5.coalescence.SoundManager;
import com.bau5.coalescence.world.Maps;

/**
 * Created by Rick on 4/1/16.
 */
public class GameScreen extends BaseScreen {
    private GameStage gameStage;

    public GameScreen(Main main, Maps map) {
        super(main, new GameStage(main, map));

        gameStage = ((GameStage) stage);
    }

    public GameScreen(Main main) {
        this(main, Maps.Testing);
    }

    @Override
    public void render(float delta) {
        if (gameStage.getNextLevel() != null) {
            main.switchToScreen(new GameScreen(main, gameStage.getNextLevel()));
        } else {
            super.render(delta);

            if (((GameStage) stage).isPaused()) {
                gameStage.getBatch().begin();
                font.draw(gameStage.getBatch(), "Paused", getWidth() / 2, getHeight() - 15);
                gameStage.getBatch().end();
            }
        }
    }

    @Override
    public void initialize() {
        super.initialize();

        gameStage.initialize();
    }
}
