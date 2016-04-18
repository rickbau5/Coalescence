package com.bau5.coalescence.world;

import com.badlogic.gdx.maps.tiled.TiledMapTile;


/**
 * Created by Rick on 4/18/16.
 */
public class MapCell {
    private final int x;
    private final int y;
    private final TiledMapTile tile;
    private final TiledMapObject object;

    public MapCell(int x, int y, TiledMapTile tile, TiledMapObject object) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.object = object;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TiledMapObject getObject() {
        return object;
    }

    public TiledMapTile getTile() {
        return tile;
    }

    public boolean hasObject() {
            return object != null;
        }
}
