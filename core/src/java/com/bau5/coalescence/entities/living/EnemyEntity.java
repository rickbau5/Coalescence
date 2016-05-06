package com.bau5.coalescence.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bau5.coalescence.EnemyAttributes;
import com.bau5.coalescence.EnemyStats;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class EnemyEntity extends LivingEntity {
    private final TextureRegion textureRegion;

    public EnemyEntity(int type, float x, float y) {
        super(type, new PositionComponent(x, y), EnemyStats.forType(type), EnemyAttributes.forType(type));

        this.textureRegion = new TextureRegion(new Texture(Gdx.files.internal(String.format("textures/enemy/%d.png", type))));
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent entityCollision = (EntityCollisionEvent) event;

            GameEntity otherEntity = entityCollision.getOtherEntity(this);
            if (otherEntity instanceof PlayableCharacter) {
                this.damage(((LivingEntity) entityCollision.getOtherEntity(this)).getAttackDamage());
                return;
            }

            if (otherEntity instanceof ProjectileEntity && ((ProjectileEntity) otherEntity).isFriendly()) {
                this.damage(((ProjectileEntity) otherEntity).getDamage());
            }

        }
    }

    @Override
    public void onDeath() {}

    @Override
    public void onSpawn() {

    }

    @Override
    public String toString() {
        return "Enemy entity " + type;
    }
}
