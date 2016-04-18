package com.bau5.coalescence.entities;

import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.events.Event;

/**
 * Created by Rick on 4/18/16.
 */
public class ReplayableCharacter extends GameEntity {
    public ReplayableCharacter(PlayableCharacter clone) {
        super(clone.pos, clone.attributes);

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
