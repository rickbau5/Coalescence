package com.bau5.coalescence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bau5.coalescence.entities.EnemyEntity;
import com.bau5.coalescence.entities.PlayableCharacter;
import com.bau5.coalescence.world.Maps;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.WorldRenderer;

/**
 * Created by Rick on 4/1/16.
 */
public class GameStage extends Stage {
    private World world;
    private WorldRenderer worldRenderer;

    private InputHandler inputHandler;

    public PlayableCharacter player;

    private boolean paused = false;

    public GameStage() {
        super(new ScalingViewport(Scaling.fit, Constants.sizeX, Constants.sizeY,
                new OrthographicCamera(Constants.sizeX, Constants.sizeY)));

        getCamera().setToOrtho(false, Constants.sizeX, Constants.sizeY);
        getCamera().update();

        this.inputHandler = new InputHandler(this);
        this.addListener(inputHandler);

        this.world = new World(Maps.Testing);
        this.worldRenderer = new WorldRenderer(world, this);

        setupPlayableCharacters();
//        addTestEntities();
    }

    private void setupPlayableCharacters() {
        this.player = new PlayableCharacter(1.5f, 1.5f, 7, 7);
        world.spawnEntity(player);
    }

    private void addTestEntities() {
        world.spawnEntity(new EnemyEntity(7, 7));
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

    public void togglePaused() {
        this.paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
