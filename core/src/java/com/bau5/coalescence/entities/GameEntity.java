package com.bau5.coalescence.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;


/**
 * Base entity class for this game. Common functionality that all game objects share
 * is present in this base class.
 *
 * Created by Rick on 4/2/16.
 */
public class GameEntity extends Entity {
    protected Engine engine;
    private PositionComponent pos;

    public GameEntity(Engine engine, PositionComponent pos, AttributeComponent attrib) {
        super();
        this.engine = engine;
        this.pos = pos;

        this.add(pos);
        this.add(attrib);

        this.engine.addEntity(this);
    }

    public GameEntity(Engine engine, int x, int y, int w, int h, Color color) {
        this(engine, new PositionComponent(x, y), new AttributeComponent(w, h, color));
    }

    public void setPosition(int x, int y) {
        pos.x_$eq(x);
        pos.y_$eq(y);
    }
}
