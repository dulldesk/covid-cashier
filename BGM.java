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
        try {
          clip.start();
        } catch (Exception e) {}
    }
    /**
     * Pause Method
     */
    public void pause() {
        try {
          this.currentFrame = this.clip.getMicrosecondPosition();
          clip.stop();
        } catch (Exception e) {}
    }
    /**
     * Resume Method
     */
    public void resume() {
        try {
          clip.close();
          audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(Utility.RES_NAME + "/bgm/"+name+".wav"));
          clip.open(audioInputStream); 
          clip.loop(Clip.LOOP_CONTINUOUSLY);
          clip.setMicrosecondPosition(currentFrame);
          clip.start();
        } catch(Exception e) {}
    }
    /**
     * Stop method
     */
    public void stop() {
        try {
          clip.stop();
          clip.close();
        } catch(Exception e) {}
    }
}