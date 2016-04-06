package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.VelocityComponent;

/**
 * Created by Rick on 4/5/2016.
 */
public class ProjectileEntity extends GameEntity {
    public ProjectileEntity(float x, float y) {
        super(new PositionComponent(x, y), new AttributeComponent(10, 3, Color.GREEN));

        this.add(new VelocityComponent(0f, 0f));
    }

    @Override
    public void onDeath() {
        System.out.println("Destroying projectile at ");
    }
}
