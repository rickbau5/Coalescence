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
    private final Direction facing;

    public FireProjectileAction(GameEntity firingEntity, int type, float posX, float posY, Direction facing) {
        this.firingEntity = firingEntity;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.facing = facing;
    }

    @Override
    public void execute() {
        super.execute();
        Vector2 vec = Direction.getOffsetForDirection(facing);
        firingEntity.world.spawnEntity(new ProjectileEntity(type, posX + vec.x / 2, posY + vec.y / 2, vec.scl(4f), Direction.toDegrees(facing)).setFiredBy(firingEntity));
    }
}
