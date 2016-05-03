package com.bau5.coalescence.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.*;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.ProjectileEntity;
import com.bau5.coalescence.entities.ReplayableCharacter;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;
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
        MoveAction action = super.moveInDirection(dir);
        performAction(action, true);
        return action;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == Event.EventType.EntityCollision) {
            EntityCollisionEvent collision = (EntityCollisionEvent) event;
            GameEntity otherEntity = collision.getOtherEntity(this);
            if (otherEntity instanceof ProjectileEntity) {
                //TODO Log death event!
                this.die();
            } else if (otherEntity instanceof EnemyEntity) {
                this.damage(((EnemyEntity) otherEntity).getAttackDamage());
                Action last = getLastAction();
                if (last instanceof MoveAction) {
                    ((MoveAction) last).undo();
                }
            }
        } else if (event.type == Event.EventType.EntityObjectCollision) {
            ((EntityObjectCollisionEvent) event).handlePlayerCollision();
        }
    }

    public void useMainAbility() {
        if (type == 1) {
             Direction dir = getDirectionFacing();
            Vector2 vec = Direction.getOffsetForDirection(dir);
            world.spawnEntity(new ProjectileEntity(1, pos.x() + vec.x, pos.y() + vec.y, vec.scl(4f), Direction.toDegrees(dir)));
        }
    }

    @Override
    public void onDeath() {
        this.world.replacePlayableCharacter(this, new ReplayableCharacter(this));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
