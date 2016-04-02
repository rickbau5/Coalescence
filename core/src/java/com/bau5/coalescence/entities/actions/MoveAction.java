package com.bau5.coalescence.entities.actions;

import com.badlogic.gdx.math.Vector2;

/**
 * MoveAction is a simple action that moves an entity from
 * their position to the target position instanteously
 *
 * Created by Rick on 4/2/16.
 */
public class MoveAction extends Action {
    int targetX, targetY;

    public MoveAction(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public MoveAction(Vector2 vec) {
        this((int)vec.x, (int)vec.y);
    }

    @Override
    public void execute() {
        actor.setPosition(targetX, targetY);
    }

    @Override
    public String toString() {
        return String.format("[MoveAction: %d, %d]", targetX, targetY);
    }
}