package com.bau5.coalescence.ui.screens;

import com.bau5.coalescence.GameStage;
import com.bau5.coalescence.Main;
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
        this(main, Maps.One);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (((GameStage) stage).isPaused()) {
            gameStage.getBatch().begin();
            font.draw(gameStage.getBatch(), "Paused", getWidth() / 2, getHeight() - 10);
            gameStage.getBatch().end();
        }
    }
}