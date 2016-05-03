package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.world.objects.triggers.TriggerObject;

/**
 * Created by Rick on 4/18/2016.
 */
public class TriggerCollisionEvent extends EntityObjectCollisionEvent {
    private TriggerObject triggerObject;

    /**
     * Event for an entity colliding with a map object
     *
     * @param entity         The source entity of the event
     * @param triggerObject The object collided with.
     */
    public TriggerCollisionEvent(GameEntity entity, TriggerObject triggerObject) {
        super(entity, triggerObject);

        this.triggerObject = triggerObject;
    }

    @Override
    public void handlePlayerCollision() {
        triggerObject.activateTrigger();
    }
}
