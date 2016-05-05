package com.bau5.coalescence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Rick on 5/4/2016.
 */
public class SoundManager {
    private HashMap<String, Sound[]> soundMap;
    private Random rand;

    public SoundManager() {
        this.soundMap = new HashMap<>();
        this.rand = new Random();
    }

    public void playSound(String name) {
        this.playSound(name, 1.0f, 1.0f);
    }

    /**
     * Plays a sound
     *
     * @param name Sound name
     * @param volume volume
     * @param pitch pitch between 0.5f and 2f
     */
    public void playSound(String name, float volume, float pitch) {
        Sound[] sounds = this.soundMap.get(name);
        if (sounds != null && sounds.length > 0) {
            Sound sound = sounds[rand.nextInt(sounds.length)];
            if (sound != null) {
                sound.play(volume, pitch, 0);
            }
        } else {
            System.out.println("Sound not found: " + name);
        }
    }

    public void initialize() {
        registerSound("magic-missile", "projectile.magic_missile");
        registerSound("arrow-launch",  "projectile.arrow_launch");
        registerSound("rope-break", "objects.rope_break");
        registerSound("gate-close", "objects.gate_close");
        registerSoundWithAlternates("arrow-hit", new String[] {"projectile.arrow_hit_1", "projectile.arrow_hit_2", "projectile.arrow_hit_3"});
    }

    public Sound registerSound(String name, String filePath) {
        filePath = this.formatFilePath(filePath);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(filePath));

        if (sound != null) {
            this.soundMap.put(name, new Sound[]{ sound });
        } else {
            System.out.printf("Failed registering new sound %s, path %s\n", name, filePath);
        }

        return sound;
    }

    public Sound[] registerSoundWithAlternates(String name, String[] paths) {
        ArrayList<Sound> sounds = new ArrayList<>(paths.length);

        for (String path : paths) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(formatFilePath(path)));
            if (sound == null) {
                System.out.printf("Failed loading sound %s, path %s\n", name, path);
            } else {
                sounds.add(sound);
            }
        }

        Sound[] arr = sounds.toArray(new Sound[sounds.size()]);
        if (sounds.size() > 0) {
            this.soundMap.put(name, arr);
        }

        return arr;
    }

    private String formatFilePath(String path) {
        return String.format("sounds/%s.mp3", path.replace('.', '/'));
    }

    public void dispose() {
        soundMap.values().forEach(sounds -> {
            for (Sound sound : sounds) {
                sound.dispose();
            }
        });
    }

    public static SoundManager instance = new SoundManager();
}
