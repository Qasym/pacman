package com.Game.audio;

import javax.sound.sampled.*;

// A class for managing sound assets
public class AudioManager {
    private static final int numberOfMusic = 4;
    private static int idx = 0;
    private static final AudioInputStream[] sounds = new AudioInputStream[numberOfMusic];

    private static Clip clip;
    private static boolean musicOn = true;

    /*
    * We first check if the setting is on, if it's not, we don't play the music
    * if it is on, we get a Clip object, and open an audio-file(if it's not already open)
    * */
    synchronized public static void playMenuMusic() {
        if (musicOn) {
            try {
                clip = ((clip == null) ? AudioSystem.getClip() : clip);
                if (!clip.isOpen()) clip.open(sounds[0]); // do not reopen the same file again, if it is open
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                clip.setFramePosition(0);
                clip.start();

//                if (!clip.isActive()) throw new RuntimeException("Music clip isn't active");

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(103);
            }
        }
    }

    synchronized public static void stopMusic() {
        try {
            if (clip.isActive()) {
                clip.stop();
//                 clip.close(); // I don't know why it doesn't work if I do this
            } else {
                throw new RuntimeException("Something wrong with the clip");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(103);
        }
    }

    public static void setMusic(boolean musicOnOff) {
        musicOn = musicOnOff;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static void addMusic(AudioInputStream music) {
        if (idx < numberOfMusic) {
            sounds[idx] = music;
            idx++;
        }
    }
}