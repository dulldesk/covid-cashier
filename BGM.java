import java.io.*;
import javax.sound.sampled.*;

public class BGM extends Thread {
    Clip clip;
    AudioInputStream audioInputStream;
    static FloatControl gainControl;

    private BGM(String name) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/src/bgm/"+name+".wav"));
        } catch(Exception e) {}
    }
    public static void play(String name) {
        new BGM(name).start();
    }

    @Override
    public void run() {
        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.VOLUME);
        } catch(Exception e) {}
    }
    public static void fadeOut() {
    }
}