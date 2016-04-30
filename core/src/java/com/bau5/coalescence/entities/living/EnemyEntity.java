package com.bau5.coalescence.entities.living;

import com.bau5.coalescence.*;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class EnemyEntity extends LivingEntity {
    public EnemyEntity(int type, float x, float y) {
        super(type, new PositionComponent(x, y), EnemyStats.forType(type), EnemyAttributes.forType(type));
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent entityCollision = (EntityCollisionEvent) event;

            if (entityCollision.getOtherEntity(this) instanceof PlayableCharacter) {
                this.damage(((LivingEntity) entityCollision.getOtherEntity(this)).getAttackDamage());
            }
        }
    }

    @Override
    public void onDeath() {}
}
