package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/19/16.
 */
public class ExitObject extends ControlObject {
    public ExitObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);
    }

    @Override
    public void executeControl() {
        System.out.println("Reached the end of the level!");

        Gdx.app.exit();
    }
}
