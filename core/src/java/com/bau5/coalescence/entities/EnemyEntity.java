package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class EnemyEntity extends GameEntity {
    public EnemyEntity(PositionComponent pos, AttributeComponent attrib) {
        super(pos, attrib);
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("Unimplemented enemy collision.");
    }

    public EnemyEntity(int x, int y) {
        this(new PositionComponent(x, y), new AttributeComponent(16, 16, Color.BLACK));
    }

    @Override
    public void onDeath() {
        System.out.println("Removing Enemy.");
    }
}
