package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.entities.GameEntity;


/**
 * Class for actions for GameEntities
 * Created by Rick on 4/2/16.
 */
public abstract class Action {
    protected GameEntity actor;
    protected long recordedTime;

    public GameEntity setActor(GameEntity entity) {
        this.actor = entity;

        return this.actor;
    }

    public void execute() {
        if (actor != null && recordedTime == 0) {
            recordedTime = actor.world.getWorldStep();
        }
    }

    public GameEntity getActor() {
        return actor;
    }

    public long getRecordedTime() {
        return recordedTime;
    }

    public Action setRecordedTime(long time) {
        this.recordedTime = time;

        return this;
    }
}
