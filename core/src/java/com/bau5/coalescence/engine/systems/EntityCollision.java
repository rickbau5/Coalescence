package com.bau5.coalescence.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.bau5.coalescence.AttributeComponent;
import com.bau5.coalescence.PositionComponent;
import com.bau5.coalescence.World;
import com.bau5.coalescence.entities.GameEntity;
import com.bau5.coalescence.entities.PlayerEntity;
import com.bau5.coalescence.entities.events.EntityCollisionEvent;
import com.bau5.coalescence.entities.events.EntityObjectCollisionEvent;
import com.bau5.coalescence.entities.events.EntityTerrainCollisionEvent;

/**
 * Created by Rick on 4/5/2016.
 */
public class EntityCollision extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<AttributeComponent> am = ComponentMapper.getFor(AttributeComponent.class);

    // Rectangles, reused to avoid excessive GC
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private final World world;

    /**
     * A system for detecting collisions between entities, other entities, and tiles.
     *
     * @param world the world, for checking tiles.
     */
    public EntityCollision(World world) {
        super(Family.all(PositionComponent.class, AttributeComponent.class).get());

        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEntities()) {
            GameEntity gameEntity = (GameEntity) entity;
            PositionComponent positionComponent = pm.get(gameEntity);
            AttributeComponent attributeComponent = am.get(gameEntity);

            int x = (int) positionComponent.x();
            int y = (int) positionComponent.y();

            // Check if on tile with a collidable object
            World.MapCell cell = world.getCellAt(x, y);
            if (cell != null && cell.getObject() != null) {
                gameEntity.handleEvent(new EntityObjectCollisionEvent(gameEntity, cell.getObject()));
            }

            if (!(entity instanceof PlayerEntity)) {
                // Check if collide with tile
                if (world.isTileCollidable(x, y)) {
                    gameEntity.handleEvent(new EntityTerrainCollisionEvent(gameEntity));
                    continue;
                }

                //Check if collision with other entity
                mapToRectangle(r1, positionComponent, attributeComponent);
                for (Entity otherEntity : getEngine().getEntities()) {
                    if (otherEntity == entity || !(otherEntity instanceof GameEntity)) continue;        // Skip this entity
                    GameEntity otherGameEntity = (GameEntity) otherEntity;
                    PositionComponent otherPos = pm.get(otherGameEntity);
                    AttributeComponent otherAttrib = am.get(otherGameEntity);
                    mapToRectangle(r2, otherPos, otherAttrib);
                    if (r1.overlaps(r2)) {
                        EntityCollisionEvent event = new EntityCollisionEvent(gameEntity, otherGameEntity);
                        gameEntity.handleEvent(event);
                        otherGameEntity.handleEvent(event);
                    }
                }
            }
        }
    }

    /**
     * Projects an entity's bounding box onto a rectangle, for maths
     * @param rectangle The rectangle to project onto.
     * @param pos The position component for the entity.
     * @param attrib The attribute component for the entity.
     * @return The rectangle provided, but updated.
     */
    public Rectangle mapToRectangle(Rectangle rectangle, PositionComponent pos, AttributeComponent attrib) {
        float x = pos.x();
        float y = pos.y();
        float w = attrib.width();
        float h = attrib.height();
        rectangle.set(x - (w / 2) / 16, y - (h / 2) / 16, w / 16, h / 16f);
        return rectangle;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {}
}
