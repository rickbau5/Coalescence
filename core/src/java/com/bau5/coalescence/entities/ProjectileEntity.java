package com.bau5.coalescence.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.*;
import com.bau5.coalescence.entities.events.DestructibleCollisionEvent;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.Event;
import com.bau5.coalescence.entities.living.EnemyEntity;
import com.bau5.coalescence.entities.living.PlayableCharacter;
import com.bau5.coalescence.entities.living.ReflectorEnemy;

/**
 * Created by Rick on 4/5/2016.
 */
public class ProjectileEntity extends GameEntity {
    private VelocityComponent velocity;
    private TextureRegion textureRegion;

    private boolean friendly = false;
    private GameEntity firedBy;

    private int damage = 10;

    private float originalX, originalY, originalRotation;

    public ProjectileEntity(int type, float x, float y, Vector2 vec, float rotation) {
        super(type, new PositionComponent(x, y), new AttributeComponent(10, 3, rotation));

        this.originalX = vec.x;
        this.originalY = vec.y;
        this.originalRotation = rotation;
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
    public boolean handleEvent(Event event) {
         switch (event.type) {
            case EntityCollision:
                GameEntity otherEntity = ((EntityCollisionEvent) event).getOtherEntity(this);
                if (firedBy != null) {
                    if (isFriendly() && (otherEntity instanceof PlayableCharacter || otherEntity instanceof ReplayableCharacter || otherEntity instanceof ReflectorEnemy)) {
                        break;
                    } else if ((!isFriendly() && otherEntity instanceof EnemyEntity)) {
                        break;
                    }
                }
            case EntityStaticCollision:
                this.die();
                return true;

            case EntityObjectCollision:
                if (event instanceof DestructibleCollisionEvent) {
                    this.die();
                    ((DestructibleCollisionEvent) event).handleEntityCollision();
                    return true;
                }
                break;
            default:
                // TODO some
        }

        return false;
    }

    @Override
    public void onDeath() {
        switch (type) {
            case 0:
                SoundManager.instance.playSound("arrow-hit");
            default:
                break;
        }
    }

    @Override
    public void onSpawn() {
        switch (type) {
            case 1:
                SoundManager.instance.playSound("magic-missile", 1, 1);
                break;
            default:
        }

        if (firedBy instanceof PlayableCharacter || firedBy instanceof ReplayableCharacter) {
            this.friendly = true;
        }

        velocity.vx_$eq(originalX);
        velocity.vy_$eq(originalY);
        attributes.rotation_$eq(originalRotation);
    }

    public GameEntity getFiredBy() {
        return this.firedBy;
    }

    public ProjectileEntity setFiredBy(GameEntity entity) {
        this.firedBy = entity;
        return this;
    }

    public ProjectileEntity setIsFriendly(boolean flag) {
        this.friendly = flag;
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


    public VelocityComponent getVelocity() {
        return velocity;
    }

    @Override
    public String toString() {
        return "Projectile Entity " + type;
    }
}
