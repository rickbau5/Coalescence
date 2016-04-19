package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.world.objects.TiledMapObject;

/**
 * Created by Rick on 4/18/2016.
 */
public class TriggerableObject extends TiledMapObject {
    public TriggerableObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);
    }

    public void trigger() {
        Vector2 vec = Direction.getOffsetForDirection(this.getDirectionFacing());
        world.spawnEntity(new ProjectileEntity(getX() + vec.x + 0.5f, getY() + vec.y + 0.5f, vec.scl(4f)));
    }
}
