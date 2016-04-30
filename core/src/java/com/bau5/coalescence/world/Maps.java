package com.bau5.coalescence.world;

/**
 * Created by Rick on 4/18/16.
 */
public enum Maps {
    Testing("level-test", "terrain", "terrain", "objects"),
    One("level-1", "terrain", "terrain", "objects");

    private final String name;
    private final String terrainLayerName;
    private final String terrainTileSetName;

    private final String objectLayerName;

    Maps(String name, String terrainLayerName, String terrainTileSetName, String objectLayerName) {
        this.name = name;
        this.terrainLayerName = terrainLayerName;
        this.terrainTileSetName = terrainTileSetName;
        this.objectLayerName = objectLayerName;
    }

    public String getPath() {
        return String.format("maps/%s.tmx", name);
    }

    public String getTerrainLayerName() {
        return terrainLayerName;
    }

    public String getTerrainTileSetName() {
        return terrainTileSetName;
    }

    public String getObjectLayerName() {
        return objectLayerName;
    }
}
