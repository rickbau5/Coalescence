package com.bau5.coalescence.entities.actions;

import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;

/**
 * Created by Rick on 5/8/2016.
 */
public class FireProjectileAction extends Action {
    private final GameEntity firingEntity;
    private final int type;
    private final float posX;
    private final float posY;
    private final Vector2 velocity;
    private final float rotation;

    public FireProjectileAction(GameEntity firingEntity, int type, float posX, float posY, Direction facing, Vector2 offset) {
        this(firingEntity, type, posX + offset.x / 2, posY + offset.y / 2, offset.scl(4f), Direction.toDegrees(facing));
    }

    public FireProjectileAction(GameEntity firingEntity, int type, float posX, float posY, Vector2 velocity, float rotation) {
        this.firingEntity = firingEntity;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.rotation = rotation;
    }

    @Override
    public void execute() {
        super.execute();

        ProjectileEntity projectileEntity = new ProjectileEntity(type, posX, posY, velocity, rotation);
        projectileEntity.setFiredBy(firingEntity);
        firingEntity.world.spawnEntity(projectileEntity, false);
    }
}
