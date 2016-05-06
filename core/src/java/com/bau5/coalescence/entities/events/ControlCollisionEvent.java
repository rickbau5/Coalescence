package com.bau5.coalescence.entities.events;


import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.world.objects.ControlObject;

/**
 * Created by Rick on 4/19/16.
 */
public class ControlCollisionEvent extends EntityObjectCollisionEvent {
    private final ControlObject controlObject;

    /**
     * An event for collision of two entities.
     *
     * @param entity         the primary entity.
     * @param controlObject  the control object.
     */
    public ControlCollisionEvent(GameEntity entity, ControlObject controlObject) {
        super(entity, controlObject);

        this.controlObject = controlObject;
    }


    @Override
    public void handleEntityCollision() {
        controlObject.executeControl();
    }
}
