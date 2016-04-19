package com.bau5.coalescence.entities.actions;

/**
 * MoveAction is a simple action that moves an entity from
 * their position to the target position instantaneously
 *
 * Created by Rick on 4/2/16.
 */
public class MoveAction extends Action {
    private float targetX, targetY;

    public MoveAction(float x, float y) {
        this.targetX = x;
        this.targetY = y;
    }

    @Override
    public void execute() {
        super.execute();

        if (getActor().world.isTileCollidable((int)targetX, (int) targetY)) {
            return;
        }
        this.actor.setPosition(targetX, targetY);
    }

    @Override
    public String toString() {
        return String.format("[MoveAction: %f, %f]", targetX, targetY);
    }
}