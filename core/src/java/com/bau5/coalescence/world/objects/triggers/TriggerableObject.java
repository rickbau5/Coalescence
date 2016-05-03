package com.bau5.coalescence.world.objects.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.objects.TiledMapObject;

/**
 * Created by Rick on 4/18/2016.
 */
public abstract class TriggerableObject extends TiledMapObject {
    public TriggerableObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);
    }

    public abstract void trigger();

    public static TriggerableObject build(World world, TextureMapObject object) {
        String name = object.getName();
        String type = (String)object.getProperties().get("type");

        if (name.equals("launcher")) {
            return new ProjectileLauncher(Integer.parseInt(type), world, object);
        }

        if (name.equals("gate")) {
            return new Gate(world, object, new TextureRegion(new Texture(Gdx.files.internal("textures/gate_retracted.png"))));
        }

        return null;
    }
}