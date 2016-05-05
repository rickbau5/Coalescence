package com.bau5.coalescence.world.objects.triggers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.SoundManager;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.objects.Stateful;

/**
 * Created by Rick on 5/3/2016.
 */
public class Gate extends TriggerableObject implements Stateful {
    private boolean active;
    private TextureRegion inactiveTexture;

    public Gate(World world, TextureMapObject mapObject, TextureRegion inactiveTexture) {
        super(world, mapObject);

        this.active = true;
        this.inactiveTexture = inactiveTexture;
    }

    @Override
    public void trigger() {
        this.active = false;
        world.addTriggeredEvent(this);
        SoundManager.instance.playSound("gate-close");
    }

    @Override
    public TextureRegion getCurrentTexture() {
        return active ? getMapObject().getTextureRegion() : inactiveTexture;
    }

    @Override
    public void reset() {
        this.active = true;
    }

    @Override
    public boolean isCollidable() {
        return active;
    }
}
