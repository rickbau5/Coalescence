package com.bau5.coalescence.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.World;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.PlayerEntity;

/**
 * Created by Rick on 4/5/2016.
 */
public class EntityCollision extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<AttributeComponent> am = ComponentMapper.getFor(AttributeComponent.class);

    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private final World world;

    public EntityCollision(World world) {
        super(Family.all(PositionComponent.class, AttributeComponent.class).get());

        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEntities()) {
            if (entity instanceof PlayerEntity) continue;
            if (entity instanceof GameEntity) {
                GameEntity gameEntity = (GameEntity) entity;
                PositionComponent positionComponent = pm.get(gameEntity);
                AttributeComponent attributeComponent = am.get(gameEntity);

                // Check if collide with tile
                if (world.isTileCollidable(((int) positionComponent.x()), (int) positionComponent.y())) {
                    gameEntity.die();
                    continue;
                }

                //Check if collision with other entity
                mapToRectangle(r1, positionComponent, attributeComponent);
                for (Entity otherEntity : getEngine().getEntities()) {
                    if (otherEntity == entity) continue;
                    PositionComponent otherPos = pm.get(otherEntity);
                    AttributeComponent otherAttrib = am.get(otherEntity);
                    mapToRectangle(r2, otherPos, otherAttrib);
                    if (r1.overlaps(r2)) {
                        System.out.println("Collided with entity!");
                        gameEntity.die();
                    }
                }
            }
        }
    }

    public Rectangle mapToRectangle(Rectangle rectangle, PositionComponent pos, AttributeComponent attrib) {
        float x = pos.x();
        float y = pos.y();
        float w = attrib.width();
        float h = attrib.height();
        rectangle.set(x - (w / 2) / 16, y - (h / 2) / 16, w / 16, h / 16f);
        return rectangle;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {}
}
