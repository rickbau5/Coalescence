package com.bau5.coalescence.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.DespawnAction;
import com.bau5.coalescence.entities.actions.MoveAction;
import com.bau5.coalescence.entities.events.Event;
import com.bau5.coalescence.world.World;

import java.util.LinkedList;


/**
 * Base entity class for this game. Common functionality that all game objects share
 * is present in this base class.
 *
 * Created by Rick on 4/2/16.
 */
public abstract class GameEntity extends Entity {
    public World world = null;
    public final PositionComponent pos;
    public final AttributeComponent attributes;

    protected LinkedList<Action> actions;
    protected LinkedList<Action> playback;

    public final int type;

    protected Direction visualFacing = Direction.Left;
    protected Direction facing = Direction.Left;

    public GameEntity(int type, PositionComponent pos, AttributeComponent attrib) {
        super();
        this.pos = pos;
        this.attributes = attrib;
        this.type = type;

        this.add(pos);
        this.add(attrib);

        this.actions = new LinkedList<>();
        this.playback = new LinkedList<>();
    }

    public abstract TextureRegion getTextureRegion();

    public GameEntity(int type, float x, float y, int w, int h, float rotation) {
        this(type, new PositionComponent(x, y), new AttributeComponent(w, h, rotation));
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void performAction(Action action, boolean record) {
        action.setActor(this);
        action.execute();
        if (record) actions.addLast(action);
    }

    public Action getLastAction() {
        return actions.getLast();
    }

    public void beginPlayback() {
        if (!playback.isEmpty()) playback.clear();
        playback.addAll(actions);
    }

    public void performStep(long worldStep) {
        if (playback.isEmpty()) return;
        if (playback.peek().getRecordedTime() <= worldStep) {
            Action action = playback.pop();

            action.execute();
        }
    }

    public void setPosition(float x, float y) {
        if (x < 0) {
            x = 0;
        } else if (x > 15) {  //TODO un-hard code these bounds
            x = 15;
        }
        if (y < 0) {
            y = 0;
        } else if (y > 15) {
            y = 15;
        }
        if (y != pos.y()) {
            facing = y > pos.y() ? Direction.Up : Direction.Down;
        }
        if (x != pos.x()) {
            visualFacing = facing = x > pos.x() ? Direction.Right : Direction.Left;
        }
        pos.x_$eq(x);
        pos.y_$eq(y);
    }

    public Vector2 getTiledPosition() {
        return new Vector2((int)pos.x(), (int)pos.y());
    }

    public MoveAction moveInDirection(Direction dir) {
        float xOff = 0;
        float yOff = 0;
        switch (dir) {
            case Up:
                yOff = 1;
                break;
            case Down:
                yOff = -1;
                break;
            case Left:
                xOff = -1;
                break;
            case Right:
                xOff = 1;
                break;
        }
        return new MoveAction(pos.x() + xOff, pos.y() + yOff);
    }

    /**
     * Abstract function for handling of events. Descendants
     * must implement their own collision logic.
     *
     * Note: The primary entity of the event may not be this
     * entity. Use event.getOtherEntity(this) to access the
     * other entity of the event.
     *
     * @param event The event to handle.
     */
    public abstract void handleEvent(Event event);

    public void die() {
        this.createDespawnEvent(world.getWorldStep());
        onDeath();

        if (world != null) world.removeEntity(this);
    }

    public abstract void onDeath();

    public void reset() {
        MoveAction action = (MoveAction)actions.getFirst();
        performAction(action, false);
    }

    public boolean shouldRecordSpawn() {
        return actions.size() == 0;
    }

    public void createDespawnEvent(long worldStep) {
        actions.add(new DespawnAction(this, worldStep));
    }

    public Direction getDirectionFacing() {
        return facing;
    }

    public boolean isFlipped() {
        return this.visualFacing == Direction.Right;
    }
}
