package com.bau5.coalescence.world.objects.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.entities.actions.TriggerObjectAction;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.objects.Stateful;
import com.bau5.coalescence.world.objects.TiledMapObject;

import java.util.ArrayList;

/**
 * Created by Rick on 4/18/2016.
 */
public class TriggerObject extends TiledMapObject implements Stateful {
    private ArrayList<Vector2> links;

    private boolean trigger = true;

    private TextureRegion activeTexture;
    private TextureRegion inactiveTexture;

    public TriggerObject(World world, TextureMapObject mapObject, TextureRegion inactiveTexture) {
        super(world, mapObject);

        this.links = new ArrayList<>();
        for (String string : mapObject.getProperties().get("triggers", String.class).split(";")) {
            String[] locationProperty = string.split(",");
            links.add(new Vector2(Integer.parseInt(locationProperty[0]), Integer.parseInt(locationProperty[1])));
        }

        this.activeTexture = mapObject.getTextureRegion();
        this.inactiveTexture = inactiveTexture == null ? mapObject.getTextureRegion() : inactiveTexture;
    }

    public void activateTrigger() {
        if (trigger) {
            for (Vector2 link : links) {
                TiledMapObject mapObject = world.getCellAt(((int) link.x), ((int) link.y)).getObject();
                if (mapObject instanceof TriggerableObject) {
                    world.addAction(new TriggerObjectAction(this));
                    ((TriggerableObject) mapObject).trigger();
                    trigger = false;
                } else {
                    System.out.println("Trigger not found!!!");
                    System.out.println("mapObject = " + mapObject);
                    System.out.println("this = " + this);
                }
            }
        }
    }

    public void replayActivateTrigger() {
        trigger = false;
    }

    @Override
    public TextureRegion getCurrentTexture() {
        return trigger ? activeTexture : inactiveTexture;
    }

    @Override
    public void reset() {
        trigger = true;
    }

    public static TriggerObject build(World world, TextureMapObject object) {
        if (object.getName().equals("rope")) {
            return new TriggerObject(world, object, new TextureRegion(new Texture(Gdx.files.internal("textures/rope_broken.png"))));
        }

        return new TriggerObject(world, object, null);
    }
}
