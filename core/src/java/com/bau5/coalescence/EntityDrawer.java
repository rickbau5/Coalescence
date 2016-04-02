package com.bau5.coalescence;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

            renderer.setColor(Color.RED);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.rect(position.x() - width / 2 + 16, position.y() - height / 2 + 16, width, height);
            renderer.end();
        }
    }
}
