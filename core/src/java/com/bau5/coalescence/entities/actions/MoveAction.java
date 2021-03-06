package com.bau5.coalescence.entities.actions;

import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.entities.events.DestructibleCollisionEvent;
import com.bau5.coalescence.world.objects.Crate;
import com.bau5.coalescence.world.objects.TiledMapObject;

/**
 * MoveAction is a simple action that moves an entity from
 * their position to the target position instantaneously
 *
 * Created by Rick on 4/2/16.
 */
public class MoveAction extends Action implements Undoable {
    private float prevX, prevY;
    private float targetX, targetY;

    public MoveAction(float x, float y) {
        this.targetX = x;
        this.targetY = y;
    }

    @Override
    public void execute() {
        super.execute();

        TiledMapObject object = getActor().world.getCellAt((int) targetX, ((int) targetY)).getObject();

        if (object != null && object instanceof Crate) {
            ((Crate) object).handleEntityCollision(new DestructibleCollisionEvent(getActor(), ((Crate) object)));
        } else if (getActor().world.isTileCollidable((int)targetX, (int) targetY)) {
            return;
        }

        Vector2 pos = this.actor.getTiledPosition();
        this.prevX = pos.x;
        this.prevY = pos.y;
        this.actor.setPosition(targetX, targetY);
    }

    @Override
    public String toString() {
        return String.format("[MoveAction: %f, %f]", targetX, targetY);
    }

    @Override
    public void undo() {
        this.actor.performAction(new MoveAction(prevX + 0.5f, prevY + 0.5f), true);
    }
}