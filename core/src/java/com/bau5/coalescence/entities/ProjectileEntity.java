package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.VelocityComponent;
import com.bau5.coalescence.entities.events.Event;

/**
 * Created by Rick on 4/5/2016.
 */
public class ProjectileEntity extends GameEntity {
    private VelocityComponent velocity;

    public ProjectileEntity(float x, float y, Vector2 vec) {
        super(0, new PositionComponent(x, y), new AttributeComponent(10, 3, Color.GREEN));

        this.velocity = new VelocityComponent(vec.x, vec.y);
        this.add(velocity);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.type) {
            case EntityCollision:
            case EntityStaticCollision:
                this.die();
                break;
            default:
                // TODO some
        }
    }

    @Override
    public void onDeath() {

    }
}
