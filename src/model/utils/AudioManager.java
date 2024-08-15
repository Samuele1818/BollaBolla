package model.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioManager {
    private static AudioManager instance;

    // Theme audio clip, can stop and resume playing
    private Clip clipTheme;

    /**
     * Make constructor private for singleton pattern
     */
    private AudioManager() {
    }

    /**
     * Get instance of the class
     *
     * @return instance of AudioManager
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Stop theme clip
     */
    public void stop() {
        if (clipTheme != null && clipTheme.isRunning()) {
            clipTheme.stop();
        }
    }

    /**
     * Play an audio clip
     *
     * @param filename name of the audio clip file
     */
    public void play(String filename) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filename));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Play theme clip audio (loop)
     *
     * @param filename name of the audio clip file
     */
    public void playMenu(String filename) {

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filename));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            clipTheme = AudioSystem.getClip();
            clipTheme.open(audioIn);
            clipTheme.loop(Clip.LOOP_CONTINUOUSLY);
            clipTheme.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
            e1.printStackTrace();
        }
    }
}

