package com.bau5.coalescence;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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

    private ArrayList<Integer> collidables;
    private ArrayList<TiledMapObject> tiledMapObjects;

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

    public void removeEntity(GameEntity entity) {
        entity.setWorld(null);
        engine.removeEntity(entity);
    }

    public ArrayList<TiledMapObject> getTiledMapObjects() {
        return tiledMapObjects;
    }

    public void loadMap(Maps mapDef) {
        this.map = loader.load(mapDef.getPath());
        this.terrainLayer = ((TiledMapTileLayer) this.map.getLayers().get(mapDef.getTerrainLayerName()));

        this.collidables = new ArrayList<>();
        for (TiledMapTile tile : this.map.getTileSets().getTileSet(mapDef.getTerrainTileSetName())) {
            if (tile.getProperties().containsKey("collidable")) {
                collidables.add(tile.getId());
            }
        }

        MapObjects objects = this.map.getLayers().get(mapDef.getObjectLayerName()).getObjects();
        this.tiledMapObjects = new ArrayList<>(objects.getCount());
        for (MapObject mapObject : objects) {
            if (mapObject instanceof TextureMapObject) {
                TextureMapObject object = ((TextureMapObject) mapObject);
                tiledMapObjects.add(new TiledMapObject(object));
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
        return collidables.contains(getTileAt(x, y).getId());
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
        Testing("level-test", "terrain", "terrain", "objects");

        private final String name;
        private final String terrainLayerName;
        private final String terrainTileSetName;

        private final String objectLayerName;

        Maps(String name, String terrainLayerName, String terrainTileSetName, String objectLayerName) {
            this.name = name;
            this.terrainLayerName = terrainLayerName;
            this.terrainTileSetName = terrainTileSetName;
            this.objectLayerName = objectLayerName;
        }

        public String getPath() {
            return String.format("maps/%s.tmx", name);
        }

        public String getTerrainLayerName() {
            return terrainLayerName;
        }

        public String getTerrainTileSetName() {
            return terrainTileSetName;
        }

        public String getObjectLayerName() {
            return objectLayerName;
        }
    }

    public class TiledMapObject {
        private final float x, y;
        private final float rotation;

        public TiledMapObject(TextureMapObject mapObject) {
            float r = mapObject.getRotation();
            if (mapObject.getRotation() < 0) {
                r = 360 + r;
            }

            float xOff = 0f;
            float yOff = 0f;
            if (r > 90f) {
                xOff -= 1f;
            }
            if (r <= 180 && r >= 90) {
                yOff -= 1f;
            }
            this.x = ((mapObject.getX() * 2) / Constants.tileSize) + xOff;
            this.y = ((mapObject.getY() * 2) / Constants.tileSize) + yOff;
            this.rotation = r;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getRotation() {
            return rotation;
        }

        public Direction getDirectionFacing() {
            return Direction.fromDegrees(rotation);
        }
    }
}
