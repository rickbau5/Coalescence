package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/6/2016.
 */
public class SpawnAction extends Action {
    private final float x;
    private final float y;
    public SpawnAction(GameEntity entity) {
        this.setActor(entity);
        this.x = entity.pos.x();
        this.y = entity.pos.y();
    }

    @Override
    public void execute() {
        super.execute();
        actor.setPosition(x, y);
        actor.world.addEntity(actor);
        actor.beginPlayback();
        actor.onSpawn();
    }

    @Override
    public String toString() {
        return String.format("Spawn {%s} at %d", actor.toString(), getRecordedTime());
    }
}
