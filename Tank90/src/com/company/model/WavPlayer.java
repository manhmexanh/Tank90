package com.company.model;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WavPlayer {
    private Clip clip;
    public WavPlayer(String name){
        try {
            URL url=getClass().getResource("/res/raw/"+name+".wav");
            AudioInputStream stream= AudioSystem.getAudioInputStream(url);
            clip=AudioSystem.getClip();
            clip.open(stream);
        }catch (IOException|UnsupportedAudioFileException |LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public void loop(int count){clip.loop(count);}
    public void playSound(){
        if(clip!=null&&!clip.isRunning()){
            clip.start();
        }
    }
    public void stopSound(){
        if(clip!=null&&clip.isRunning()){
            clip.stop();
        }
    }
}
