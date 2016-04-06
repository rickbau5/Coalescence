package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.entities.actions.MoveAction;


/**
 * Created by Rick on 4/2/16.
 */
public class PlayerEntity extends GameEntity {
    public PlayerEntity(float x, float y, int w, int h) {
        super(x, y, w, h, Color.RED);
    }

    @Override
    public MoveAction moveInDirection(Direction dir) {
        MoveAction action = super.moveInDirection(dir);
        performAction(action, true);
        return action;
    }

    @Override
    public void onDeath() {
        System.out.println("Removing player.");
    }
}
