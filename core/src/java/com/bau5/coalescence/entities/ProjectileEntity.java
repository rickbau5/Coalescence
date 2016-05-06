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

            case EntityObjectCollision:
                if (event instanceof DestructibleCollisionEvent) {
                    this.die();
                    ((DestructibleCollisionEvent) event).handleEntityCollision();
                }
                break;
            default:
                // TODO some
        }
    }

    @Override
    public void onDeath() {
        this.damage = 0;
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

    @Override
    public String toString() {
        return "Projectile Entity " + type;
    }
}
