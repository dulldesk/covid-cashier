/**
  * Plays a sound effect
  * <p> Public usage
  * SoundFX.play(String name, int duration)
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.2
  */

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundFX extends Thread {
	/**
	  * Duration of the sound effect, in milliseconds
	  */
	private int duration;

	/**
	  * Input stream with the audio
	  */
	private AudioInputStream audioInput;

	/**
	  * Constructs a SoundFX
	  * @param name 		the name of the file, including the extension
	  * @param duration 	the duration of the sound effect in ms
	  */
	private SoundFX(String name, int duration) {
		this.duration = duration; 
		try {
			audioInput = AudioSystem.getAudioInputStream(this.getClass().getResource(Utility.RES_NAME + "/sfx/"+name+".wav"));
		} 
		catch (UnsupportedAudioFileException e) {}
		catch (IOException e) {}
	}

	/**
	  * Plays the sound effect
	  * @param name 		the name of the file, including the extension
	  * @param duration 	the duration of the sound effect in ms
	  */
	public static void play(String name, int duration) {
		new SoundFX(name, duration).start();
	}

	@Override
	public void run() {
		try {
			Clip sfx = AudioSystem.getClip();
			sfx.open(audioInput);
			sfx.start();
			Thread.sleep(duration);
			sfx.close();
		} 
		catch (LineUnavailableException e) {}
		catch (InterruptedException e) {}
		catch (IOException e) {}
	}
}
