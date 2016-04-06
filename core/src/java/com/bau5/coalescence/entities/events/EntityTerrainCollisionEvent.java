package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;


/**
 * Created by Rick on 4/6/2016.
 */
public class EntityTerrainCollisionEvent extends Event {
    /**
     * An event to represent an entity colliding with terrain.
     *
     * @param actingEntity The entity.
     */
    public EntityTerrainCollisionEvent(GameEntity actingEntity) {
        super(EventType.EntityStaticCollision, actingEntity);
    }
}
