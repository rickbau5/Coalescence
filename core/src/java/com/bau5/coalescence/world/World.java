package com.bau5.coalescence.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.bau5.coalescence.engine.systems.EntityCollision;
import com.bau5.coalescence.engine.systems.EntityMovement;
import com.bau5.coalescence.engine.systems.EntityReplayer;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.PlayableCharacter;
import com.bau5.coalescence.entities.ReplayableCharacter;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.actions.SpawnAction;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by Rick on 4/4/16.
 */
public class World implements Disposable {
    private Engine engine;
    private TiledMap map;
    private TmxMapLoader loader;

    private TiledMapTileLayer terrainLayer;

    // List of id's of collidable tiles, used for faster lookup
    private ArrayList<Integer> collidables;
    // List of objects on the loaded map, represented in an adapter class
    private ArrayList<TiledMapObject> tiledMapObjects;

    private MapCell[][] mapCells;

    private LinkedList<Action> worldActions;
    private LinkedList<Action> replayActions;

    private long worldStep = 0;

    private boolean replaying = false;
    private PlayableCharacter activePlayer = null;

    /**
     * Intializes the world, creating the engine and it's base systems,
     * and loading the world as per the Maps enum.
     *
     * @param mapToLoad Enum representing map to load
     */
    public World(Maps mapToLoad) {
        this.engine = new Engine();
        this.loader = new TmxMapLoader();

        this.worldActions = new LinkedList<>();
        this.replayActions = new LinkedList<>();

        loadMap(mapToLoad);

        // All worlds will need an movement and collision system
        addSystemToEngine(new EntityCollision(this));
        addSystemToEngine(new EntityMovement(this));
        addSystemToEngine(new EntityReplayer(this));
    }

    /**
     * Update the world's logic.
     *
     * @param delta time passed since last update
     */
    public void update(float delta) {
        worldStep += 1;
        performStep(worldStep);

        engine.update(delta);
    }

    private void performStep(long worldStep) {
        if (replayActions.isEmpty()) return;
        if (replayActions.peek().getRecordedTime() <= worldStep) {
            Action action = replayActions.pop();

            System.out.println("Executing world action: " + action + " for world step " + worldStep);
            action.execute();
        }
    }

    public GameEntity getEntityAt(Vector2 position) {
        for (Entity entity : engine.getEntities()) {
            if (entity instanceof GameEntity) {
                GameEntity gameEntity = (GameEntity) entity;

                if (gameEntity.getTiledPosition().equals(position)) {
                    return gameEntity;
                }
            }
        }

        return null;
    }

    public void replacePlayableCharacter(PlayableCharacter character, ReplayableCharacter replayer) {
        // TODO
        setActivePlayer(null);
        for (Action worldAction : worldActions) {
            if (worldAction.getActor() == character) {
                worldAction.setActor(replayer);
            }
        }
    }

    public void spawnEntity(GameEntity entity) {
        entity.setWorld(this);
        SpawnAction spawnAction = new SpawnAction(entity);

        replayActions.add(spawnAction);
        worldActions.add(spawnAction);
    }

    public void addEntity(GameEntity entity) {
        if (entity.world == null) entity.setWorld(this);
        engine.addEntity(entity);
        entity.performAction(new MoveAction(entity.pos.x(), entity.pos.y()), entity.shouldRecordSpawn());
    }

    public void removeEntity(GameEntity entity) {
        engine.removeEntity(entity);
    }

    public boolean isReplaying() {
        return replaying;
    }

    public void replay() {
        replaying = true;
        worldStep = 0;

        reset();
        if (activePlayer != null) {
            activePlayer.die();
        }

        if (!replayActions.isEmpty()) replayActions.clear();
        replayActions.addAll(worldActions);

        engine.removeAllEntities();
    }

    public void reset() {
        for (Entity ent: engine.getEntities()) {
            if (ent instanceof GameEntity) {
                ((GameEntity)ent).reset();
            }
        }
    }

    public MapCell getCellAt(int x, int y) {
        if (x < 0 || x >= getMapWidth()) return null;
        if (y < 0 || y >= getMapHeight()) return null;
        return mapCells[x][y];
    }

    /**
     * Checks if the tile at the given coords is collidable
     *
     * @param x map coordinant
     * @param y map coordinant
     * @return  false if out of bounds, or not collidable
     *          true if collidable
     */
    public boolean isTileCollidable(int x, int y) {
        MapCell cell = getCellAt(x, y);
        return cell != null && collidables.contains(cell.getTile().getId());
    }

    public long getWorldStep() {
        return worldStep;
    }

    public int getMapWidth() {
        return terrainLayer.getWidth();
    }

    public int getMapHeight() {
        return terrainLayer.getHeight();
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

        this.mapCells = new MapCell[terrainLayer.getWidth()][terrainLayer.getHeight()];
        for (int i = 0; i < terrainLayer.getWidth(); i++) {
            for (int j = 0; j < terrainLayer.getHeight(); j++) {
                TiledMapObject object = null;
                for (TiledMapObject obj : tiledMapObjects) {
                    if (obj.getX() == i && obj.getY() == j) {
                        object = obj;
                    }
                }
                mapCells[i][j] = new MapCell(i, j, terrainLayer.getCell(i, j).getTile(), object);
            }
        }
    }

    public void addSystemToEngine(EntitySystem system) {
        this.engine.addSystem(system);
    }

    public PlayableCharacter getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(PlayableCharacter newActivePlayer) {
        if (this.activePlayer != null) {
            this.activePlayer.setActive(false);
        }

        this.activePlayer = newActivePlayer;
        if (newActivePlayer != null) {
            newActivePlayer.setActive(true);
            System.out.println("Activated " + newActivePlayer);
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public TiledMap getMap() {
        return map;
    }

    public ArrayList<TiledMapObject> getTiledMapObjects() {
        return tiledMapObjects;
    }

    @Override
    public void dispose() {
        this.map.dispose();
    }
}
