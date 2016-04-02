package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.entities.GameEntity;


/**
 * Class for actions for GameEntities
 * Created by Rick on 4/2/16.
 */
public abstract class Action {
    protected GameEntity actor;

    public void setActor(GameEntity entity) {
        this.actor = entity;
    }

    public abstract void execute();

    public GameEntity getActor() {
        return actor;
    }
}
