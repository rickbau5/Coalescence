package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/18/2016.
 */
public class TriggerObject extends TiledMapObject {
    private final int linkX;
    private final int linkY;

    private boolean trigger = true;

    public TriggerObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);

        String[] locationProperty = mapObject.getProperties().get("triggers", String.class).split(",");
        this.linkX = Integer.parseInt(locationProperty[0]);
        this.linkY = Integer.parseInt(locationProperty[1]);
    }

    public void activateTrigger() {
        if (trigger) {
            TiledMapObject mapObject = world.getCellAt(linkX, linkY).getObject();
            if (mapObject instanceof TriggerableObject) {
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
