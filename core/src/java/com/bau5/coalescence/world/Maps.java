package com.bau5.coalescence.world;

import com.bau5.coalescence.SoundManager;

/**
 * Created by Rick on 4/18/16.
 */
public enum Maps {
    Testing("level-test", null, null),
    One("level-1", null, null),
    Two("level-2", null, "dripping"),
    Three("level-3", null, "dripping");

    private final String name;
    private final String terrainLayerName;
    private final String terrainTileSetName;
    private final String objectLayerName;

    private final String musicName;
    private final String ambienceName;

    Maps(String fileName, String terrainLayerName, String terrainTileSetName, String objectLayerName, String music, String ambience) {
        this.name = fileName;
        this.terrainLayerName = terrainLayerName;
        this.terrainTileSetName = terrainTileSetName;
        this.objectLayerName = objectLayerName;

        this.musicName = music;
        this.ambienceName = ambience;
    }

    Maps(String fileName, String music, String ambience) {
        this(fileName,  "terrain", "terrain", "objects", music, ambience);
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

    public void playMusicForLevel() {
        if (musicName != null) {
            SoundManager.instance.playMusic(musicName);
        }
    }

    public void playAmbienceForLevel() {
        if (ambienceName != null) {
            SoundManager.instance.playAmbience(ambienceName);
        }
    }
}
