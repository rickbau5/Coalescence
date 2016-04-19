package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/18/2016.
 */
public class DespawnAction extends Action {
    public DespawnAction(GameEntity entity, long worldStep) {
        this.setActor(entity);
        this.recordedTime = worldStep;
    }

    @Override
    public void execute() {
        actor.world.removeEntity(actor);
    }
}
