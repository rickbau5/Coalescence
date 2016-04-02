package com.bau5.coalescence.entities;

import com.badlogic.ashley.core.Engine;
import com.bau5.coalescence.entities.actions.Action;
import com.bau5.coalescence.entities.actions.MoveAction;

import java.util.LinkedList;

/**
 * Created by Rick on 4/2/16.
 */
public class PlayerEntity extends GameEntity {
    private LinkedList<Action> actions;

    private LinkedList<Action> playback;

    public PlayerEntity(Engine engine, float x, float y, int w, int h) {
        super(engine, x, y, w, h);

        this.actions = new LinkedList<>();
        this.playback = new LinkedList<>();

        performAction(new MoveAction(x, y));
    }

    public void performAction(Action action) {
        action.setActor(this);
        action.execute();
        actions.addLast(action);
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
}
