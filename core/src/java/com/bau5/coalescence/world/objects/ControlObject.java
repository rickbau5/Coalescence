package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/19/16.
 */
public abstract class ControlObject extends TiledMapObject {
    public ControlObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);
    }

    public abstract void executeControl();
}
