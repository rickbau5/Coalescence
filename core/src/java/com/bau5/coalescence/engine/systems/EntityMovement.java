package com.bau5.coalescence.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.VelocityComponent;
import com.bau5.coalescence.world.World;
import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/5/2016.
 */
public class EntityMovement extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    private final World world;

    public EntityMovement(World world) {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = pm.get(entity);
        VelocityComponent velocityComponent = vm.get(entity);

        positionComponent.add(velocityComponent.vx() * deltaTime, velocityComponent.vy() * deltaTime);

        if (positionComponent.x() >= world.getMapWidth() || positionComponent.x() < 0
                || positionComponent.y() >= world.getMapHeight() || positionComponent.y() < 0) {
            if (entity instanceof GameEntity) {
                ((GameEntity) entity).die();
            }
        }
    }
}
