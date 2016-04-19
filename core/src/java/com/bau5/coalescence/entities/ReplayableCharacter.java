package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.events.Event;

/**
 * Created by Rick on 4/18/16.
 */
public class ReplayableCharacter extends GameEntity {
    public ReplayableCharacter(PlayableCharacter clone) {
        super(new PositionComponent(clone.pos.x(), clone.pos.y()), new AttributeComponent(clone.attributes.width(), clone.attributes.height(), clone.attributes.color()));
        this.attributes.color_$eq(Color.BLUE);

        for (Action action : clone.actions) {
            action.setActor(this);
            this.actions.addLast(action);
        }

        setWorld(clone.world);
    }

    @Override
    public void handleEvent(Event event) {
        // Nothing; only replaying
    }

    @Override
    public void onDeath() {
        // Nothing; only replaying
    }
}
