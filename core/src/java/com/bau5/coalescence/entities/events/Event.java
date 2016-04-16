package com.bau5.coalescence.entities.events;

import com.bau5.coalescence.entities.GameEntity;

/**
 * Created by Rick on 4/6/2016.
 */
public abstract class Event {
    public final EventType type;
    public final GameEntity entity;

    /**
     * The base event class.
     *
     * @param type The type of event.
     * @param entity The source entity of the event
     */
    public Event(EventType type, GameEntity entity) {
        this.type = type;
        this.entity = entity;
    }

    /**
     * Enum representing possible event types.
     */
    public enum EventType {
        EntityStaticCollision, EntityCollision, EntityObjectCollision
    }
}

