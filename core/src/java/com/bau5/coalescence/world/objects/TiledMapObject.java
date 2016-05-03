package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.Constants;
import com.bau5.coalescence.Direction;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/18/16.
 */
public class TiledMapObject {
    private final int originX, originY;
    private final int x, y;
    private final float rotation;

    private final TextureMapObject mapObject;

    public final World world;

    public TiledMapObject(World world, TextureMapObject mapObject) {
        float r = mapObject.getRotation();

        while (r < 0) r = r + 360;

        if (mapObject.getRotation() > 360) {
            r = r % 360;
        }

        int xOff = 0;
        int yOff = 0;
        if (r > 90f) {
            xOff -= 1;
        }
        if (r < 180 && r >= 90) {
            yOff -= 1;
        }

        this.originX = (int)((mapObject.getX() * 2) / Constants.tileSize);
        this.originY = (int)((mapObject.getY() * 2) / Constants.tileSize);

        this.x = originX + xOff;
        this.y = originY + yOff;
        this.rotation = r;
        this.mapObject = mapObject;

        this.world = world;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TextureMapObject getMapObject() {
        return mapObject;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public float getRotation() {
        return rotation;
    }

    public Direction getDirectionFacing() {
        return Direction.fromDegrees(rotation);
    }
}