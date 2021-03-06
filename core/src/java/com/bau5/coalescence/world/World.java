package com.bau5.coalescence.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.GameStage;
import com.bau5.coalescence.engine.systems.EntityCollision;
import com.bau5.coalescence.engine.systems.EntityMovement;
import com.bau5.coalescence.engine.systems.EntityReplayer;
import com.bau5.coalescence.entities.Enemy;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ReplayableCharacter;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.actions.SpawnAction;
import com.bau5.coalescence.entities.actions.TriggeredAction;
import com.bau5.coalescence.entities.living.PlayableCharacter;
import com.bau5.coalescence.world.objects.ExitObject;
import com.bau5.coalescence.world.objects.Stateful;
import com.bau5.coalescence.world.objects.TiledMapObject;
import com.bau5.coalescence.world.objects.TiledMapObjectBuilder;
import com.bau5.coalescence.world.objects.triggers.TriggerObject;
import com.bau5.coalescence.world.objects.triggers.TriggerableObject;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by Rick on 4/4/16.
 */
public class World implements Disposable {
    private final GameStage stage;
    private Engine engine;
    private TiledMap map;
    private TmxMapLoader loader;

    private TiledMapTileLayer terrainLayer;

    // List of id's of collidable tiles, used for faster lookup
    private ArrayList<Integer> collidables;
    // List of objects on the loaded map, represented in an adapter class
    private ArrayList<TiledMapObject> tiledMapObjects;

    private MapCell[][] mapCells;

    private LinkedList<Action> replayActions;

    private LinkedList<Action> actionList;

    private long worldStep = 0;

    private boolean replaying = false;

    private boolean canReplay = true;
    private PlayableCharacter activePlayer = null;
    private ArrayList<PlayableCharacter> characters;

    /**
     * Intializes the world, creating the engine and it's base systems,
     * and loading the world as per the Maps enum.
     *
     * @param mapToLoad Enum representing map to load
     */
    public World(GameStage stage, Maps mapToLoad) {
        this.stage = stage;
        this.engine = new Engine();
        this.loader = new TmxMapLoader();

        this.replayActions = new LinkedList<>();
        this.actionList = new LinkedList<>();

        this.characters = new ArrayList<>();

        loadMap(mapToLoad);

        // All worlds will need a movement and collision system
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
            action.execute();

            if (!actionList.contains(action)) {
                actionList.add(action);
            }
        }
    }

    public GameEntity getEntityAt(Vector2 position) {
        GameEntity atPosition = null;
        for (Entity entity : engine.getEntities()) {
            if (entity instanceof GameEntity) {
                GameEntity gameEntity = (GameEntity) entity;

                if (gameEntity.getTiledPosition().equals(position)) {
                    atPosition = gameEntity;
                }
            }
        }

        return atPosition;
    }

    public void replacePlayableCharacter(PlayableCharacter character, ReplayableCharacter replayer) {
        if (character == getActivePlayer()) {
            setActivePlayer(null);
        }
    }

    public void spawnEntity(GameEntity entity, boolean record) {
        entity.setWorld(this);
        SpawnAction spawnAction = new SpawnAction(entity);

        if (record) {
            replayActions.addFirst(spawnAction);
            actionList.add(spawnAction.setRecordedTime(worldStep));
        } else {
            spawnAction.execute();
        }
    }

    public void spawnEntity(GameEntity entity) {
        this.spawnEntity(entity, true);
    }

    public void addTriggeredEvent(TriggerableObject object) {
        Action action = new TriggeredAction(object);
        actionList.add(action.setRecordedTime(worldStep));
    }

    public void addEntity(GameEntity entity) {
        if (entity.world == null) entity.setWorld(this);
        engine.addEntity(entity);
        entity.performAction(new MoveAction(entity.pos.x(), entity.pos.y()), entity.shouldRecordSpawn());
    }

    public void removeEntity(GameEntity entity) {
        engine.removeEntity(entity);
    }

    public void addAction(Action worldAction) {
        actionList.add(worldAction);
    }

    public boolean isReplaying() {
        return replaying;
    }

    public void replay() {
        replaying = true;
        worldStep = 0;

        reset();

        if (!replayActions.isEmpty()) replayActions.clear();
        actionList.sort((a, b) -> {
            long t1 = a.getRecordedTime();
            long t2 = b.getRecordedTime();
            return (t1 < t2) ? -1 : (t1 == t2) ? 0 : 1;
        });
        replayActions.addAll(actionList);
        actionList.clear();

        engine.removeAllEntities();
        canReplay = true;
    }

    public void reset() {
        for (Entity ent: engine.getEntities()) {
            if (ent instanceof GameEntity) {
                ((GameEntity)ent).reset();
            }
        }

        for (TiledMapObject object : tiledMapObjects) {
            if (object instanceof Stateful) {
                ((Stateful) object).reset();
            }
        }

        if (activePlayer != null) {
            activePlayer = null;
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
        return cell != null && (collidables.contains(cell.getTile().getId()) || (cell.hasObject() && cell.getObject().isCollidable()));
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

    public ArrayList<PlayableCharacter> getCharacters() {
        return this.characters;
    }

    private void loadMap(Maps mapDef) {
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
                if (object.getName() == null) {
                    System.out.println("Skipping object with no name.");
                    continue;
                }

                if (mapObject.getProperties().containsKey("triggerable")) {
                    // Triggerable
                    tiledMapObjects.add(TriggerableObject.build(this, object));
                } else if (mapObject.getProperties().containsKey("triggers")) {
                    // Triggers a Triggerable
                    tiledMapObjects.add(TriggerObject.build(this, object));
                } else if (mapObject.getName().equals("exit")) {
                    tiledMapObjects.add(new ExitObject(this, object));
                } else {
                    TiledMapObject newObject = TiledMapObjectBuilder.fromName(this, object);

                    if (newObject == null) {
                        // Regular object
                        newObject = new TiledMapObject(this, object);
                    }

                    tiledMapObjects.add(newObject);
                }
            } else if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject object = (RectangleMapObject) mapObject;
                Rectangle rect = object.getRectangle();

                float xPos = (int)((rect.getX() * 2) / Constants.tileSize) + .5f;
                float yPos = (int)((rect.getY() * 2) / Constants.tileSize) + .5f;

                int type = Integer.parseInt((String)object.getProperties().get("type"));
                switch (object.getName()) {
                    case "character":
                        PlayableCharacter character = new PlayableCharacter(type, xPos, yPos, 16, 16, 0f);
                        this.characters.add(character);
                        spawnEntity(character);
                        break;
                    case "enemy":
                        spawnEntity(Enemy.forType(type, xPos, yPos));
                        break;
                    default:
                        break;
                }
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

        if (canReplay) {
            this.activePlayer = newActivePlayer;
            if (activePlayer != null) {
                activePlayer.setActive(true);
            }
            canReplay = false;
        } else {
            this.activePlayer = null;
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

    public GameStage getStage() {
        return stage;
    }
}

