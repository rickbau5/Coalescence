package com.bau5.coalescence;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.actions.MoveAction;

import java.util.ArrayList;


/**
 * Created by Rick on 4/4/16.
 */
public class World implements Disposable {
    private Engine engine;
    private TiledMap map;
    private TmxMapLoader loader;

    private TiledMapTileLayer terrainLayer;

    private ArrayList<Integer> collidableObjects;

    public World(Maps mapToLoad) {
        this.engine = new Engine();
        this.loader = new TmxMapLoader();

        loadMap(mapToLoad);
    }

    public void update(float delta) {
        engine.update(delta);
    }

    public void addEntity(GameEntity entity) {
        entity.setWorld(this);
        engine.addEntity(entity);
        entity.performAction(new MoveAction(entity.pos.x(), entity.pos.y()), true);
    }

    public void loadMap(Maps map) {
        this.map = loader.load(map.getPath());
        this.terrainLayer = ((TiledMapTileLayer) this.map.getLayers().get(0));

        this.collidableObjects = new ArrayList<>();
        for (TiledMapTile tile : this.map.getTileSets().getTileSet("terrain")) {
            if (tile.getProperties().containsKey("collidable")) {
                collidableObjects.add(tile.getId());
            }
        }
    }

    public void reset() {
        for (Entity ent: engine.getEntities()) {
            if (ent instanceof GameEntity) {
                ((GameEntity)ent).reset();
            }
        }
    }

    public TiledMapTile getTileAt(int x, int y) {
        return terrainLayer.getCell(x, y).getTile();
    }

    public boolean isTileCollidable(int x, int y) {
        return collidableObjects.contains(getTileAt(x, y).getId());
    }

    public void addSystemToEngine(EntitySystem system) {
        this.engine.addSystem(system);
    }

    public Engine getEngine() {
        return engine;
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        this.map.dispose();
    }

    public enum Maps {
        Testing("level-test");

        private final String name;

        Maps(String name) {
            this.name = name;
        }

        public String getPath() {
            return String.format("maps/%s.tmx", name);
        }
    }
}
