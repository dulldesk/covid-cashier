import java.io.*;
import javax.sound.sampled.*;

public class BGM {
    private Clip clip;
    private AudioInputStream audioInputStream;
    private Long currentFrame;
    private String name;
    public BGM(String name) {
        this.name = name;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/src/bgm/"+name+".wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {}
    }
    public void play() {
        clip.start();
    }
    public void pause() {
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
    }
    public void resume() {
        clip.close();
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/src/bgm/"+name+".wav"));
            clip.open(audioInputStream); 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {System.out.println("Resume");}
        clip.setMicrosecondPosition(currentFrame);
        clip.start();
    }
    public void stop() {
        clip.stop();
        clip.close();
    }
}