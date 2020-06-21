/**
  * Holds data of a last triggered event of some sort. Used to determine whether to acknowledge or ignore a trigger.
  * 
  * <p> 
  * Examples of usage include a character's last movement or the last time a button was clicekd
  * <p> 
  * If the character's last movement was in a different direction or greater than the refresh rate, 
  * then a character should be repainted. 
  * If the button was clicked over the given number of nanoseconds after the previous trigger,
  * then the click should be acknowledged. 
  * 
  * Last edited: 6/4/2020
  * @author Celeste
  * @version 1.1
  * @since 1.0
  */
public class LastTrigger {
	/**
	  * The last time, in nanoseconds, that the frame was repainted by a movement key binding
	  */
	private long time;
	
	/**
	  * The player's direction of movement in the last time they moved
	  */
	private char direction;
	
	/**
	  * The refresh rate of the player's walking, in nanoseconds
	  * <p> After this number of nanoseconds, the frame can repaint.
	  */
	private final long REFRESH_RATE = (long)6.5e7; 

	/**
	  * Constructor
	  * @param t 	the last trigger time to be set
	  * @param d 	the direction of the last trigger to be set
	  */
	public LastTrigger (long t, char d) {
		time = t;
		direction = d;
	}

	/**
	  * Constructor with default values
	  */
	public LastTrigger() {
		time = System.nanoTime();
	}

	public long getTime() {
		return time;
	}

	public char getDirection() {
		return direction;
	}

	/**
	  * @param currDir 	the player's current direction
	  * @return whether the panel should be repainted
	  */
	public boolean compareNow(char currDir) {
		boolean shouldRefresh = direction != currDir || System.nanoTime() - time > REFRESH_RATE;
		if (shouldRefresh) {
			time = System.nanoTime();
			direction = currDir;
		}
		return shouldRefresh;
	}

	/**
	  * @return whether the panel should be repainted
	  */
	public boolean compareNow() {
		boolean shouldRefresh = System.nanoTime() - time > REFRESH_RATE;
		if (shouldRefresh) time = System.nanoTime();
		return shouldRefresh;
	}
}