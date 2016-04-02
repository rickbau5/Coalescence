package com.bau5.coalescence;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;


/**
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

    public GameEntity(Engine engine, float x, float y, int w, int h) {
        this(engine, new PositionComponent(x, y), new AttributeComponent(w, h));
    }

    public void setPosition(float x, float y) {
        pos.x_$eq(x);
        pos.y_$eq(y);
    }
}
