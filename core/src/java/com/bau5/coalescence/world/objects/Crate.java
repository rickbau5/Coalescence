package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.entities.events.DestructibleCollisionEvent;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/5/2016.
 */
public class Crate extends DestructibleObject {
    public Crate(World world, TextureMapObject mapObject) {
        super(world, mapObject, "crate", "crate_broken");
    }

    @Override
    public void handleEntityCollision(DestructibleCollisionEvent event) {
        this.destroy();
    }
}
