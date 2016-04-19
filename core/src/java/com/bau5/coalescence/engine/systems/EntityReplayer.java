package com.bau5.coalescence.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/6/2016.
 */
public class EntityReplayer extends IteratingSystem {
    private final World world;

    public EntityReplayer(World world) {
        super(Family.all(PositionComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (!world.isReplaying()) return;
        if (entity instanceof GameEntity) {
            GameEntity gameEntity = (GameEntity) entity;
            gameEntity.performStep(world.getWorldStep());
        }
    }
}
