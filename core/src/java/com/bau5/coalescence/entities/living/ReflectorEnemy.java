package com.bau5.coalescence.entities.living;

import com.bau5.coalescence.VelocityComponent;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;

/**
 * Created by Rick on 5/6/2016.
 */
public class ReflectorEnemy extends EnemyEntity {
    public ReflectorEnemy(int type, float x, float y) {
        super(type, x, y);
    }

    @Override
    public boolean handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent coll = (EntityCollisionEvent) event;
            GameEntity otherEntity = coll.getOtherEntity(this);

            if (otherEntity instanceof ProjectileEntity && ((ProjectileEntity) otherEntity).isFriendly()) {
                ProjectileEntity projectile = (ProjectileEntity) otherEntity;
                VelocityComponent vel = projectile.getVelocity();
                vel.vx_$eq(vel.vx() * -1);
                vel.vy_$eq(vel.vy() * -1);
                projectile.setFriendly(false);
                float newRotation = projectile.attributes.rotation() - 180;
                projectile.attributes.rotation_$eq(newRotation);
                return true;
            }
        }

        return super.handleEvent(event);
    }
}
