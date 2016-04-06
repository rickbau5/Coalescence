package com.bau5.coalescence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bau5.coalescence.entities.EnemyEntity;
import com.bau5.coalescence.entities.PlayerEntity;

/**
 * Created by Rick on 4/1/16.
 */
public class GameStage extends Stage {
    private World world;
    private WorldRenderer worldRenderer;

    private InputHandler inputHandler;

    public PlayerEntity player;

    private long secondTime = TimeUtils.millis();
    private boolean replaying = false;

    public GameStage() {
        super(new ScalingViewport(Scaling.fit, Constants.sizeX, Constants.sizeY,
                new OrthographicCamera(Constants.sizeX, Constants.sizeY)));

        getCamera().setToOrtho(false, Constants.sizeX, Constants.sizeY);
        getCamera().update();

        this.inputHandler = new InputHandler(this);
        this.addListener(inputHandler);

        this.world = new World(World.Maps.Testing);
        this.worldRenderer = new WorldRenderer(world, this);

        setupPlayer();
        addTestEntities();
    }

    private void setupPlayer() {
        this.player = new PlayerEntity(1, 1, 7, 7);
        world.addEntity(player);
    }

    private void addTestEntities() {
        world.addEntity(new EnemyEntity(7, 7));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (TimeUtils.millis() - secondTime > 1000) {
            if (replaying) {
                replaying = player.performNext();
            }

            secondTime = TimeUtils.millis();
        }

        world.update(delta);
    }

    @Override
    public void draw() {
        super.draw();
        getCamera().update();

        worldRenderer.render();
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

    public void beginPlayback() {
        player.beginPlayback();
        replaying = true;
    }

    public void reset() {
        world.reset();
    }
}
