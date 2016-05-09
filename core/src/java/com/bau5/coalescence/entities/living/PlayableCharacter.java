package com.bau5.coalescence.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.CharacterStats;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.entities.ReplayableCharacter;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.actions.FireProjectileAction;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.EntityObjectCollisionEvent;
import com.bau5.coalescence.entities.events.Event;


/**
 * Created by Rick on 4/2/16.
 */
public class PlayableCharacter extends LivingEntity {
    private final TextureRegion textureRegion;
    private boolean active = false;

    public PlayableCharacter(int type, float x, float y, int w, int h, float rotation) {
        super(
            type,
            new PositionComponent(x, y),
            CharacterStats.forType(type),
            new AttributeComponent(w, h, rotation)
        );

        this.textureRegion = new TextureRegion(new Texture(Gdx.files.internal(String.format("textures/character/%d.png", type))));
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public MoveAction moveInDirection(Direction dir) {
        if (playback.isEmpty()) {
            MoveAction action = super.moveInDirection(dir);
            performAction(action, true);
            return action;
        } else {
            return null;
        }
    }

    @Override
    public boolean handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent collision = (EntityCollisionEvent) event;
            GameEntity otherEntity = collision.getOtherEntity(this);
            if (otherEntity instanceof ProjectileEntity && !((ProjectileEntity) otherEntity).isFriendly()) {
                this.damage(otherEntity, ((ProjectileEntity) otherEntity).getDamage());
                return true;
            } else if (otherEntity instanceof EnemyEntity) {
                this.damage(otherEntity, ((EnemyEntity) otherEntity).getAttackDamage());
                Action last = getLastAction();
                if (last instanceof MoveAction) {
                    ((MoveAction) last).undo();
                }
                return true;
            }
        } else if (event.type == Event.EventType.EntityObjectCollision) {
            ((EntityObjectCollisionEvent) event).handleEntityCollision();
            return true;
        }

        return false;
    }

    public void useMainAbility() {
        if (type == 1) {
            performAction(new FireProjectileAction(this, 1, pos.x(), pos.y(), this.getDirectionFacing(), Direction.getOffsetForDirection(this.getDirectionFacing())), true);
        }
    }

    @Override
    public void onDeath() {
        this.world.replacePlayableCharacter(this, new ReplayableCharacter(this));
    }

    @Override
    public void onSpawn() {
        this.health = getMaxHealth();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Playable character " + type;
    }
}
