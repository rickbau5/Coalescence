package com.bau5.coalescence.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;

import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.World;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;

import java.util.LinkedList;


/**
 * Base entity class for this game. Common functionality that all game objects share
 * is present in this base class.
 *
 * Created by Rick on 4/2/16.
 */
public abstract class GameEntity extends Entity {
    public World world = null;
    public PositionComponent pos;

    private LinkedList<Action> actions;
    private LinkedList<Action> playback;

    public GameEntity(PositionComponent pos, AttributeComponent attrib) {
        super();
        this.pos = pos;

        this.add(pos);
        this.add(attrib);

        this.actions = new LinkedList<>();
        this.playback = new LinkedList<>();

        performAction(new MoveAction(pos.x(), pos.y()), true);
    }

    public GameEntity(int x, int y, int w, int h, Color color) {
        this(new PositionComponent(x, y), new AttributeComponent(w, h, color));
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void performAction(Action action, boolean record) {
        action.setActor(this);
        action.execute();
        if (record) actions.addLast(action);
    }

    public void beginPlayback() {
        if (!playback.isEmpty()) playback.clear();
        playback.addAll(actions);
    }

    public boolean performNext() {
        if (playback.isEmpty()) return false;
        Action next = playback.pop();

        System.out.println("Executing action: " + next);
        next.execute();

        return true;
    }

    public void setPosition(int x, int y) {
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
        pos.x_$eq(x);
        pos.y_$eq(y);
    }

    public MoveAction moveInDirection(Direction dir) {
        int xOff = 0;
        int yOff = 0;
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

    public enum Direction {
        Up, Down, Left, Right
    }

    public abstract void die();

    public void reset() {
        MoveAction action = (MoveAction)actions.getFirst();
        performAction(action, false);
    }
}
