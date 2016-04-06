package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/6/2016.
 */
public class EntityCollisionEvent extends Event {
    public final GameEntity collidedEntity;

    /**
     * An event for collision of two entities.
     *
     * @param entity the primary entity.
     * @param collidedEntity the secondary, collided entity.
     */
    public EntityCollisionEvent(GameEntity entity, GameEntity collidedEntity) {
        super(Event.EventType.EntityCollision, entity);
        this.collidedEntity = collidedEntity;
    }

    /**
     * Given an entity, gets the entity that is not the one passed. If an entity
     * is passed that is not part of this event, it will return the main entity
     * for this event.
     *
     * @param gameEntity The entity to test against.
     * @return The entity that is not the one given.
     */
    public GameEntity getOtherEntity(GameEntity gameEntity) {
        if (entity == gameEntity) {
            return collidedEntity;
        }

        return entity;
    }
}
