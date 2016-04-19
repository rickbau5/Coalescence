package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.EntityObjectCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class PlayableCharacter extends GameEntity {
    private boolean active = false;

    public PlayableCharacter(float x, float y, int w, int h) {
        super(x, y, w, h, Color.FOREST);
    }

    @Override
    public MoveAction moveInDirection(Direction dir) {
        MoveAction action = super.moveInDirection(dir);
        performAction(action, true);
        return action;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent collision = (EntityCollisionEvent) event;
            GameEntity otherEntity = collision.getOtherEntity(this);
            if (otherEntity instanceof ProjectileEntity) {
                //TODO Log death event!
                this.die();
            } else if (otherEntity instanceof EnemyEntity) {
                this.die();
            }
        } else if (event.type == Event.EventType.EntityObjectCollision) {
            ((EntityObjectCollisionEvent) event).handlePlayerCollision();
        }
    }

    @Override
    public void onDeath() {
        this.world.replacePlayableCharacter(this, new ReplayableCharacter(this));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.attributes.color_$eq(Color.RED);
    }
}
