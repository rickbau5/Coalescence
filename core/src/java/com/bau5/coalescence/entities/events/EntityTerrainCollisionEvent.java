package com.bau5.coalescence.entities.events;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/6/2016.
 */
public class EntityTerrainCollisionEvent extends Event {
    private final TiledMapTile tile;
    /**
     * An event to represent an entity colliding with terrain.
     *
     * @param actingEntity The entity.
     */
    public EntityTerrainCollisionEvent(GameEntity actingEntity, TiledMapTile tile) {
        super(EventType.EntityStaticCollision, actingEntity);

        this.tile = tile;
    }

    public TiledMapTile getTile() {
        return tile;
    }
}
