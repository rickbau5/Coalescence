package com.bau5.coalescence.world;

/**
 * Created by Rick on 4/18/16.
 */
public enum Maps {
    Testing("level-test"),
    One("level-1"),
    Two("level-2");

    private final String name;
    private final String terrainLayerName;
    private final String terrainTileSetName;

    private final String objectLayerName;

    Maps(String fileName, String terrainLayerName, String terrainTileSetName, String objectLayerName) {
        this.name = fileName;
        this.terrainLayerName = terrainLayerName;
        this.terrainTileSetName = terrainTileSetName;
        this.objectLayerName = objectLayerName;
    }

    Maps(String fileName) {
        this(fileName,  "terrain", "terrain", "objects");
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
