package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.world.objects.TiledMapObject;

/**
 * Created by Rick on 4/16/2016.
 */
public abstract class EntityObjectCollisionEvent extends Event {
    protected final TiledMapObject collidedObject;

    /**
     * Event for an entity colliding with a map object
     *
     * @param entity The source entity of the event
     * @param collidedObject The object collided with.
     */
    public EntityObjectCollisionEvent(GameEntity entity, TiledMapObject collidedObject) {
        super(Event.EventType.EntityObjectCollision, entity);

        this.collidedObject = collidedObject;
    }

    public abstract void handleEntityCollision();

    public TiledMapObject getCollidedObject() {
        return collidedObject;
    }
}
