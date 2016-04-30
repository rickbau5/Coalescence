package com.bau5.coalescence.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.living.LivingEntity;

/**
 * Created by Rick on 4/1/16.
 */
public class EntityDrawer extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<AttributeComponent> am = ComponentMapper.getFor(AttributeComponent.class);

    private ShapeRenderer renderer;

    public EntityDrawer(ShapeRenderer renderer) {
        super(Family.all(PositionComponent.class, AttributeComponent.class).get());
        this.renderer = renderer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (deltaTime == 0.0f) {
            PositionComponent position = pm.get(entity);
            AttributeComponent attributes = am.get(entity);

            int width = attributes.width();
            int height = attributes.height();

            float drawX = position.x() * Constants.tileSize;
            float drawY = position.y() * Constants.tileSize;

            renderer.begin(ShapeRenderer.ShapeType.Filled);

            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                if (living.getHealth() < living.getMaxHealth()) {
                    renderer.setColor(Color.RED);

                    float percent = (float) living.getHealth() / (float) living.getMaxHealth();

                    renderer.rect(
                            ((int) position.x()) * Constants.tileSize,
                            (int) drawY + 12,
                            Constants.tileSize * percent,
                            4
                    );
                }
            }

            renderer.setColor(attributes.color());
            renderer.rect(drawX - width / 2, drawY - height / 2, width, height);
            renderer.end();
        }
    }
}
