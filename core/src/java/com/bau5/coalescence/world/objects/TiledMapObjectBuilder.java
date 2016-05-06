package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/5/2016.
 */
public class TiledMapObjectBuilder {
    public static TiledMapObject fromName(World world, TextureMapObject object) {
        switch (object.getName()) {
            case "crate": return new Crate(world, object);
            default:
                return null;
        }
    }
}
