package com.bau5.coalescence;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bau5.coalescence.entities.PlayerEntity;

/**
 * Created by Rick on 4/1/16.
 */
public class GameStage extends Stage {
    private Main main;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;

    private InputHandler inputHandler;

    private Engine engine;
    private EntityDrawer entityDrawer;

    public PlayerEntity player;

    private long secondTime = TimeUtils.millis();
    private boolean replaying = false;

    public GameStage() {
        super(new ScalingViewport(Scaling.fit, Constants.sizeX, Constants.sizeY,
                new OrthographicCamera(Constants.sizeX, Constants.sizeY)));

        this.map = new TmxMapLoader().load("maps/level-test.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.scale);
        getCamera().setToOrtho(false, Constants.sizeX, Constants.sizeY);
        getCamera().update();

        this.shapeRenderer = new ShapeRenderer();

        this.inputHandler = new InputHandler(this);
        this.addListener(inputHandler);

        this.engine = new Engine();
        this.entityDrawer = new EntityDrawer(shapeRenderer);
        engine.addSystem(entityDrawer);

        this.player = new PlayerEntity(engine, 1, 1, 7, 7);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (replaying) {
            if (TimeUtils.millis() - secondTime > 1000) {
                replaying = player.performNext();
                secondTime = TimeUtils.millis();
            }
        }

        engine.update(delta);
    }

    @Override
    public void draw() {
        super.draw();
        getCamera().update();

        mapRenderer.setView(getCamera());
        mapRenderer.render();

        entityDrawer.update(0.0f);
        drawTileOutline();
    }

    private void drawTileOutline() {
        Vector2 mapCoords = stageToMapCoordinates(inputHandler.getMouseLocation());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(mapCoords.x, mapCoords.y, Constants.tileSize, Constants.tileSize);
        shapeRenderer.end();
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera)super.getCamera();
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
        map.dispose();
    }

    public Vector2 stageToMapCoordinates(Vector2 stageCoords) {
        int tileSize = Constants.tileSize;
        int row = (int) stageCoords.x / tileSize;
        int col = (int) stageCoords.y / tileSize;
        return stageCoords.set(row * tileSize, col * tileSize);
    }

    public void beginPlayback() {
        player.beginPlayback();
        replaying = true;
    }
}
