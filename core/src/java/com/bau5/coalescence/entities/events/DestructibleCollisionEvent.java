package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.world.objects.DestructibleObject;

/**
 * Created by Rick on 5/5/2016.
 */
public class DestructibleCollisionEvent extends EntityObjectCollisionEvent {
    /**
     * Event for an entity colliding with a map object
     *  @param entity         The source entity of the event
     * @param collidedObject The object collided with.
     */
    public DestructibleCollisionEvent(GameEntity entity, DestructibleObject collidedObject) {
        super(entity, collidedObject);
    }

    @Override
    public void handleEntityCollision() {
        getCollidedObject().handleEntityCollision(this);
    }

    @Override
    public DestructibleObject getCollidedObject() {
        return (DestructibleObject)collidedObject;
    }
}
