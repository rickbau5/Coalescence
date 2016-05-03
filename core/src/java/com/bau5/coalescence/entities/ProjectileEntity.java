package com.bau5.coalescence.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.ProjectileStats;
import com.bau5.coalescence.VelocityComponent;
import com.bau5.coalescence.engine.systems.EntityCollision;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;
import com.bau5.coalescence.entities.living.EnemyEntity;
import com.bau5.coalescence.entities.living.PlayableCharacter;

/**
 * Created by Rick on 4/5/2016.
 */
public class ProjectileEntity extends GameEntity {
    private VelocityComponent velocity;
    private TextureRegion textureRegion;

    private boolean friendly = false;
    private int damage = 10;

    public ProjectileEntity(int type, float x, float y, Vector2 vec, float rotation) {
        super(type, new PositionComponent(x, y), new AttributeComponent(10, 3, rotation));

        this.velocity = new VelocityComponent(vec.x, vec.y);
        this.add(velocity);

        this.textureRegion = new TextureRegion(new Texture(Gdx.files.internal(String.format("textures/projectile/%d.png", type))));

        setDamage(ProjectileStats.forType(type).attackDamage());
    }

    @Override
    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.type) {
            case EntityCollision:
                GameEntity otherEntity = ((EntityCollisionEvent) event).getOtherEntity(this);
                if (isFriendly() && otherEntity instanceof PlayableCharacter) {
                    break;
                } else if (!isFriendly() && otherEntity instanceof EnemyEntity) {
                    break;
                }
            case EntityStaticCollision:
                this.die();
                break;
            default:
                // TODO some
        }
    }

    @Override
    public void onDeath() {
        this.damage = 0;
    }

    public ProjectileEntity markFriendly() {
        this.friendly = true;
        return this;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int amount) {
        this.damage = amount;
    }
}
