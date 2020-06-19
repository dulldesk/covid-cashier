/**
  * The splash screen 
  * 
  * Last edit: 6/18/2020
  * @author 	Eric
  * @version 	1.1
  * @since 		1.0
  */

import java.io.*;
import javax.sound.sampled.*;

public class BGM {
    private Clip clip;
    private AudioInputStream audioInputStream;
    private Long currentFrame;
    private String name;

    /**
	  * Constructor
	  */
    public BGM(String name) {
        this.name = name;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(Utility.RES_NAME + "/bgm/"+name+".wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {}
    }

    /**
     * Play method
     */
    public void play() {
        clip.start();
    }
    /**
     * Pause Method
     */
    public void pause() {
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
    }
    /**
     * Resume Method
     */
    public void resume() {
        clip.close();
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(Utility.RES_NAME + "/bgm/"+name+".wav"));
            clip.open(audioInputStream); 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {System.out.println("Resume");}
        clip.setMicrosecondPosition(currentFrame);
        clip.start();
    }
    /**
     * Stop method
     */
    public void stop() {
        clip.stop();
        clip.close();
    }
}