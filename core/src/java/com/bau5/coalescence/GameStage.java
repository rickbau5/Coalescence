package com.bau5.coalescence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bau5.coalescence.ui.elements.GameButton;
import com.bau5.coalescence.ui.screens.GameViewport;
import com.bau5.coalescence.world.Maps;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.WorldRenderer;

/**
 * Created by Rick on 4/1/16.
 */
public class GameStage extends Stage {
    private Main main;
    private World world;
    private WorldRenderer worldRenderer;

    public Table table;

    private InputHandler inputHandler;

    private boolean paused = false;
    private Maps loadedWorld;
    private Maps nextLevel = null;

    public GameStage(Main main, Maps map) {
        super(new GameViewport());
        this.main = main;
        this.loadedWorld = map;

        this.inputHandler = new InputHandler(this);
        this.addListener(inputHandler);

        this.world = new World(this, map);
        this.worldRenderer = new WorldRenderer(world, this);

        this.table = new Table();
        table.add(GameButton.MainMenu(main));
        table.row();
        table.add(GameButton.ReloadWorld(main));
        table.row();
        table.add(GameButton.SelectWorld(main));
        table.row();
        table.add(GameButton.Exit());
        table.setPosition(getViewport().getWorldWidth() / 2, getViewport().getWorldHeight() / 2);
        this.addActor(table);
        table.setVisible(false);
    }

    public void initialize() {
        SoundManager.instance.beginFadeMusic();
        this.loadedWorld.playMusicForLevel();
        this.loadedWorld.playAmbienceForLevel();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (SoundManager.instance.isFadingMusic()) {
            SoundManager.instance.fadeMusic(delta);
        }

        if (!paused) {
            world.update(delta);
        }
    }

    @Override
    public void draw() {
        worldRenderer.render();

        super.draw();
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera)super.getCamera();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        super.dispose();

        worldRenderer.dispose();
        world.dispose();
        SoundManager.instance.stopAmbience();
        SoundManager.instance.stopMusic();
    }

    public Vector2 stageToMapCoordinates(Vector2 stageCoords) {
        int tileSize = Constants.tileSize;
        int row = (int) stageCoords.x / tileSize;
        int col = (int) stageCoords.y / tileSize;
        return stageCoords.set(row, col);
    }

    public void togglePaused() {
        this.paused = !paused;
    }

    public void toggleInGameMenu() {
        table.setVisible(!table.isVisible());
    }

    public boolean isPaused() {
        return paused;
    }

    public Maps getLoadedWorld() {
        return this.loadedWorld;
    }

    public void moveToLevel(String level) {
        this.nextLevel = Maps.valueOf(level);
    }

    public Maps getNextLevel() {
        return nextLevel;
    }
}
