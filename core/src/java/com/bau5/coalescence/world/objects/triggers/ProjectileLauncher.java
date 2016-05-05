package com.bau5.coalescence.world.objects.triggers;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.SoundManager;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/3/2016.
 */
public class ProjectileLauncher extends TriggerableObject {
    private final int type;

    public ProjectileLauncher(int type, World world, TextureMapObject mapObject) {
        super(world, mapObject);

        this.type = type;
    }

    @Override
    public void trigger() {
        Vector2 vec = Direction.getOffsetForDirection(this.getDirectionFacing());
        world.spawnEntity(new ProjectileEntity(type, getX() + vec.x + 0.5f, getY() + vec.y + 0.5f, vec.scl(4f), this.getRotation()));
        SoundManager.instance.playSound("arrow-launch");
    }
}
