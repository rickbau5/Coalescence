package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class PlayableCharacter extends GameEntity {
    private boolean active = false;

    public PlayableCharacter(float x, float y, int w, int h) {
        super(x, y, w, h, Color.RED);
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
            if (collision.getOtherEntity(this) instanceof ProjectileEntity) {
                System.out.println("Hit by arrow");
                this.die();
            }
        } else if (event.type == Event.EventType.EntityObjectCollision) {
            System.out.println("Collision");
        }
    }

    @Override
    public void onDeath() {
        System.out.println("Removing player.");

        ReplayableCharacter newCharacter = new ReplayableCharacter(this);

        this.world.replacePlayableCharacter(this, newCharacter);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
