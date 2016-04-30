package com.bau5.coalescence.entities;

import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class EnemyEntity extends GameEntity {
    private int health = 20;

    public EnemyEntity(int type, PositionComponent pos, AttributeComponent attrib) {
        super(type, pos, attrib);
    }

    public EnemyEntity(int type, float x, float y) {
        this(type, new PositionComponent(x, y), new AttributeComponent(16, 16, Color.BLACK));
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent entityCollision = (EntityCollisionEvent) event;

            if (entityCollision.getOtherEntity(this) instanceof PlayableCharacter) {
                health -= 10;
                if (health <= 0) {
                    this.die();
                }
            }
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void onDeath() {

    }
}
