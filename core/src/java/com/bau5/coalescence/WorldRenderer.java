package com.bau5.coalescence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.bau5.coalescence.engine.systems.EntityDrawer;
import com.bau5.coalescence.engine.systems.EntityMovement;

/**
 * Created by Rick on 4/4/16.
 */
public class WorldRenderer implements Disposable {
    private final World world;
    private final GameStage stage;

    private final TiledObjectMapRenderer mapRenderer;
    private final ShapeRenderer shapeRenderer;

    private final InputHandler inputHandler;

    private final EntityDrawer entityDrawer;

    public WorldRenderer(World world, GameStage stage) {
        this.world = world;
        this.stage = stage;

        // Renderer
        this.mapRenderer = new TiledObjectMapRenderer(world.getMap(), Constants.scale);
        this.shapeRenderer = new ShapeRenderer();

        // Systems
        this.entityDrawer = new EntityDrawer(shapeRenderer);
        world.addSystemToEngine(entityDrawer);

        this.inputHandler = stage.getInputHandler();
    }

    public void render() {
        mapRenderer.setView(stage.getCamera());
        mapRenderer.render();

        entityDrawer.update(0.0f);
        drawTileOutline();
    }

    private void drawTileOutline() {
        Vector2 mapCoords = stage.stageToMapCoordinates(inputHandler.getMouseLocation());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(mapCoords.x * Constants.tileSize, mapCoords.y * Constants.tileSize, Constants.tileSize, Constants.tileSize);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
