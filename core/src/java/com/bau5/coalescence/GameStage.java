package com.bau5.coalescence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bau5.coalescence.ui.elements.GameButton;
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

    public GameStage(Main main, Maps map) {
        super(new ScalingViewport(Scaling.fit, Constants.sizeX, Constants.sizeY,
                new OrthographicCamera(Constants.sizeX, Constants.sizeY)));
        this.main = main;
        this.loadedWorld = map;

        getCamera().setToOrtho(false, Constants.sizeX, Constants.sizeY);
        getCamera().update();

        this.inputHandler = new InputHandler(this);
        this.addListener(inputHandler);

        this.world = new World(map);
        this.worldRenderer = new WorldRenderer(world, this);

        this.table = new Table();
        table.add(GameButton.MainMenu(main));
        table.row();
        table.add(GameButton.ReloadWorld(main));
        table.row();
        table.add(GameButton.Exit());
        table.setPosition(getViewport().getScreenWidth() / 2, getViewport().getScreenHeight() / 2);
        this.addActor(table);
        table.setVisible(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!paused) {
            world.update(delta);
        }
    }

    @Override
    public void draw() {
        getCamera().update();
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
}
