package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.events.Event;
import com.bau5.coalescence.entities.living.PlayableCharacter;

/**
 * Created by Rick on 4/18/16.
 */
public class ReplayableCharacter extends GameEntity {
    private final TextureRegion textureRegion;
    public ReplayableCharacter(PlayableCharacter clone) {
        super(clone.type, new PositionComponent(clone.pos.x(), clone.pos.y()), new AttributeComponent(clone.attributes.width(), clone.attributes.height(), clone.attributes.rotation()));
        this.attributes.rotation_$eq(clone.attributes.rotation());

//        for (Action action : clone.actions) {
//            action.setActor(this);
//            this.actions.addLast(action);
//        }

        setWorld(clone.world);

        this.textureRegion = clone.getTextureRegion();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public boolean handleEvent(Event event) {
        // Nothing; only replaying
        return false;
    }

    @Override
    public void onDeath() {
        // Nothing; only replaying
    }

    @Override
    public void onSpawn() {
        // Nothing
    }
}
