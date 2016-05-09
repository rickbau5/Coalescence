package com.bau5.coalescence.entities.living;

import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.actions.FireProjectileAction;

/**
 * Created by Rick on 5/8/2016.
 */
public class BossEntity extends EnemyEntity {
    public BossEntity(int type, float x, float y) {
        super(type, x, y);
    }

    @Override
    public void damage(GameEntity damager, int amount) {
        super.damage(damager, amount);

        if (world.getCharacters().size() > 0) {
            world.getCharacters().forEach(e -> {
                if (e.getHealth() > 0) {
                    float dx = e.pos.x() - this.pos.x();
                    float dy = e.pos.y() - this.pos.y();
                    double angle = Math.atan2(dy, dx) * 180 / Math.PI - 90;

                    performAction(new FireProjectileAction(this, 2, this.pos.x(), this.pos.y(), new Vector2((float)Math.cos(Math.toRadians(angle + 90)) * 1.2f, (float)Math.sin(Math.toRadians(angle + 90)) * 1.2f), (float)angle), true);
                }
            });
        }
    }
}
