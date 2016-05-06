package com.bau5.coalescence.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bau5.coalescence.Main;
import com.bau5.coalescence.ui.elements.GameButton;

/**
 * Created by Rick on 4/1/16.
 */
public abstract class BaseScreen implements Screen {
    public final Stage stage;
    protected final Main main;

    protected BitmapFont font = GameButton.Font();

    public BaseScreen(Main main, Stage stage) {
        this.main = main;
        this.stage = stage;

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    public void centerTable(Table table) {
        table.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public int getWidth() {
        return ((int) stage.getWidth());
    }

    public int getHeight() {
        return ((int) stage.getHeight());
    }

    @Override
    public void hide() {}

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
