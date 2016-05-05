package com.bau5.coalescence.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.GameStage;
import com.bau5.coalescence.InputHandler;
import com.bau5.coalescence.engine.systems.EntityDrawer;

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
        this.mapRenderer = new TiledObjectMapRenderer(world, Constants.scale);
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        // Systems
        this.entityDrawer = new EntityDrawer(stage.getBatch(), shapeRenderer);
        world.addSystemToEngine(entityDrawer);

        this.inputHandler = stage.getInputHandler();
    }

    public void render() {
        mapRenderer.setView(stage.getCamera());
        mapRenderer.render();

        if (stage.isPaused()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(0, 0, stage.getWidth(), stage.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            drawTileOutline();
        }
        entityDrawer.update(0.0f);
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
