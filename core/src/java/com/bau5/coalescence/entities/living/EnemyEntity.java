package com.bau5.coalescence.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bau5.coalescence.EnemyAttributes;
import com.bau5.coalescence.EnemyStats;
import com.bau5.coalescence.PositionComponent;
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

            if (entityCollision.getOtherEntity(this) instanceof PlayableCharacter) {
                this.damage(((LivingEntity) entityCollision.getOtherEntity(this)).getAttackDamage());
            }
        }
    }

    @Override
    public void onDeath() {}
}
