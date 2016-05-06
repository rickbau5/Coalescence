package com.bau5.coalescence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Rick on 5/4/2016.
 */
public class SoundManager {
    private HashMap<String, Sound[]> sounds;
    private HashMap<String, Music> music;
    private HashMap<String, Music> ambience;

    private Random rand;

    public SoundManager() {
        this.music = new HashMap<>();
        this.sounds = new HashMap<>();
        this.ambience = new HashMap<>();

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
        Sound[] sounds = this.sounds.get(name);
        if (sounds != null && sounds.length > 0) {
            Sound sound = sounds[rand.nextInt(sounds.length)];
            if (sound != null) {
                sound.play(volume, pitch, 0);
            }
        } else {
            System.out.println("Sound not found: " + name);
        }
    }

    public Music playMusic(String name) {
        return this.playMusic(name, true);
    }

    public Music playMusic(String name, boolean loop) {
        Music musicToPlay = music.get(name);

        if (musicToPlay == null) {
            System.out.println("Could not find music with name " + name);
        } else {
            musicToPlay.play();
            musicToPlay.setLooping(loop);
        }

        return musicToPlay;
    }

    public Music getPlayingMusic() {
        Music playing = null;

        for (Music m : music.values()) {
            if (m.isPlaying()) {
                playing = m;
                break;
            }
        }

        return playing;
    }

    public void playAmbience(String name) {
        this.playAmbience(name, true);
    }
    
    public void playAmbience(String name, boolean loop) {
        Music toPlay = ambience.get(name);

        if (toPlay != null) {
            toPlay.play();
            toPlay.setLooping(loop);
        } else {
            System.out.println("Could not find ambience " + name);
        }
    }

    public void stopAmbience() {
        ambience.values().forEach(Music::stop);
    }

    public void stopMusic() {
        Music m = getPlayingMusic();
        if (m != null) m.stop();
    }

    public void initialize() {
        registerSound("magic-missile", "projectile.magic_missile");
        registerSound("arrow-launch",  "projectile.arrow_launch");
        registerSound("rope-break", "objects.rope_break");
        registerSound("gate-close", "objects.gate_close");
        registerSoundWithAlternates("arrow-hit", new String[] {"projectile.arrow_hit_1", "projectile.arrow_hit_2", "projectile.arrow_hit_3"});

        registerMusic("theme");

        registerAmbience("dripping");
    }

    public void registerAmbience(String name) {
        Music newAmbience = Gdx.audio.newMusic(Gdx.files.internal(String.format("ambience/%s.mp3", name)));

        if (newAmbience != null) {
            ambience.put(name, newAmbience);
        } else {
            System.out.println("Could not load ambience: " + name);
        }
    }

    public void registerMusic(String name) {
        Music newMusic = Gdx.audio.newMusic(Gdx.files.internal(String.format("music/%s.mp3", name)));
        if (newMusic != null) {
            music.put(name, newMusic);
        } else {
            System.out.println("Could not load music file: " + name);
        }
    }

    public Sound registerSound(String name, String filePath) {
        filePath = this.formatSongFilePath(filePath);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(filePath));

        if (sound != null) {
            this.sounds.put(name, new Sound[]{ sound });
        } else {
            System.out.printf("Failed registering new sound %s, path %s\n", name, filePath);
        }

        return sound;
    }

    public Sound[] registerSoundWithAlternates(String name, String[] paths) {
        ArrayList<Sound> sounds = new ArrayList<>(paths.length);

        for (String path : paths) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(formatSongFilePath(path)));
            if (sound == null) {
                System.out.printf("Failed loading sound %s, path %s\n", name, path);
            } else {
                sounds.add(sound);
            }
        }

        Sound[] arr = sounds.toArray(new Sound[sounds.size()]);
        if (sounds.size() > 0) {
            this.sounds.put(name, arr);
        }

        return arr;
    }

    private String formatSongFilePath(String path) {
        return String.format("sounds/%s.mp3", path.replace('.', '/'));
    }

    public void dispose() {
        sounds.values().forEach(sounds -> {
            for (Sound sound : sounds) {
                sound.dispose();
            }
        });

        music.values().forEach(Music::dispose);
    }

    public static SoundManager instance = new SoundManager();
}
