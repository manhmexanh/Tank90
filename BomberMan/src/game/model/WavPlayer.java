package game.model;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WavPlayer {
    private Clip clip;

    public WavPlayer(String name) {
        //fis -> file -> path

        //clip -> ais -> url

        try {
            URL url = getClass().getResource("/res/raw/" + name + ".wav");

            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(stream);


        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loop(int count) {
        clip.loop(count);//Clip.LOOP_CONTINUOUSLY chay lien tuc
    }

    public void playSound() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
