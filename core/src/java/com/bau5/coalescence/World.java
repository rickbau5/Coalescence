package com.bau5.coalescence;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.bau5.coalescence.entities.GameEntity;


/**
 * Created by Rick on 4/4/16.
 */
public class World {
    private Engine engine;
    private TiledMap map;
    private TmxMapLoader loader;

    public World() {
        this.engine = new Engine();
        this.loader = new TmxMapLoader();
    }

    public World(Maps mapToLoad) {
        this();

        loadMap(mapToLoad);
    }

    public void update(float delta) {
        engine.update(delta);
    }

    public void addEntity(GameEntity entity) {
        entity.setWorld(this);
        engine.addEntity(entity);
    }

    public void loadMap(Maps map) {
        this.map = loader.load(map.getPath());
    }

    public void reset() {
        for (Entity ent: engine.getEntities()) {
            if (ent instanceof GameEntity) {
                ((GameEntity)ent).reset();
            }
        }
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
