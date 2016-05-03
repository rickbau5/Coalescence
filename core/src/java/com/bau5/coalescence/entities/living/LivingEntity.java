package com.bau5.coalescence.entities.living;

import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.StatsComponent;
import com.bau5.coalescence.entities.GameEntity;


/**
 * Created by Rick on 4/30/16.
 */
public abstract class LivingEntity extends GameEntity {
    private final StatsComponent stats;

    protected int health;

    public LivingEntity(int type, PositionComponent pos, StatsComponent statsComponent, AttributeComponent attrib) {
        super(type, pos, attrib);
        this.stats = statsComponent;
        this.health = statsComponent.getMaxHealth();
    }

    public int getMaxHealth() {
        return stats.getMaxHealth();
    }

    public int getAttackDamage() {
        return stats.attackDamage();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void damage(int amount) {
        setHealth(Math.max(0, health - amount));
        if (health <= 0) {
            this.die();
        }
    }

    @Override
    public void reset() {
        super.reset();
        health = stats.getMaxHealth();
    }
}