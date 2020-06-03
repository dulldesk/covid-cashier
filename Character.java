/**
  * A character, in the game
  * 
  * Last edit: 5/30/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public abstract class Character {
	/**
	  * The chosen name of the character
	  */
	protected String name;

	/**
	  * The character's gender, represented by its first character (capitalized)
	  */
	protected char gender;

	/**
	  * The current x-coordinate of the character (top-left corner)
	  */
	protected int x_coord;

	/**
	  * The current y-coordinate of the character (top-left corner)
	  */
	protected int y_coord;

	/**
	  * The direction that the character is facing. Referenced by the first character of the four cardinal directions. 
	  */
	protected char direction;

	/**
	  * The type of clothing that the character currently wearing, denotated by the first character of said type (capitalized)
	  */
	protected char clothingType;

	/**
	  * The type of personal protective equipment the character currently wearing, denotated by the first character of said type (capitalized)
	  */
	protected String protectiveEquipment;

	/**
	  * The image sprites to be used for a character's movement.
	  * <p> The key is a String, formatted as: "d-s[-c-p]", where: 
	  * <ul>
	  *   <li> d is the direction that the character is facing (north, south, east, or west)
	  *   <li> s is the type of step that the character is taking (a natural number)
	  *   <li> c is the type of clothing to be drawn (casual or work). 
	  *   <li> p specifies the personal protective equipment that the player has (mask and/or gloves). Represented by the addition of a character (M, G, or MG). 
	  * </ul>
	  * <p> Optional parameters are denoted by [] in the key. 
	  * <p> The value of each key is the corresponding image represented by the key. 
	  */
	protected Map<String,Image> steps;

	/**
	  * The number of types of steps for a sprite's direction of movement
	  */
	protected int stepNo;

	/**
	  * The width of a sprite image, in pixels
	  */
	public static final int WIDTH = 100;

	/**
	  * The height of a sprite image, in pixels
	  */
	public static final int HEIGHT = 100;

	/**
	  * The number of types of steps for a sprite's direction of movement
	  */
	protected static final int TOTAL_STEPS = 4;

	/**
	  * Holds the last time that the character's drawing was repainted. "Slows down" the movement of the character. 
	  * <p> For more information, see the declaration of LastTrigger
	  */
	protected LastTrigger lastMvTime;

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param type 	the name of the player sprite that was chosen
	  * @param gender 	the gender of the Character chosen
	  */
	public Character(String name, String type, char gender) {
		this(name,type,gender,'C',"N");
	}

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 		the Character's name, as chosen by the user
	  * @param type 		the name of the player sprite that was chosen
	  * @param gender 		the gender of the Character chosen
	  * @param clothing 	the clothing of the Character chosen
	  * @param equipment	the protective equipement of the Character chosen
	  */
	  public Character(String name, String type, char gender, char clothing, String equipment) {
		this.name = name;
		this.gender = gender;
		this.clothingType = clothing;
		this.protectiveEquipment = equipment;

		// Default values
		this.direction = 'S';
		this.x_coord = 100;
		this.y_coord = 100;

		stepNo = 0;

		steps = new HashMap<String,Image>();
 		lastMvTime = new LastTrigger(0,direction);

		loadSprites();
	}

	/**
	  * Loads the image files into the steps HashMap for each character subclass
	  */
	protected abstract void loadSprites();

	/**
	  * Get the sprite for the current step, given the character's other data as stored in the member variables
	  * @param step 	the step number. Prerequisite: a valid step; 0 <= step < TOTAL_STEPS
	  * @return the corresponding sprite for the current character's step movement
	  */
	protected Image getSprite(int step) {
		return steps.get(direction+"-"+(step+1)+"-"+clothingType+"-"+protectiveEquipment);
	}

	/**
	  * Set player coordinates
	  * @param x 	the x coordinate
	  * @param y 	the y coordinate
	  */
	public void setCoordinates(int x, int y) {
		x_coord = x;
		y_coord = y;		
	}

	/**
	  * Set player direction
	  * @param dir	the direction
	  */
	public void setDirection(char dir) {
		direction = dir;		
	}

	public int getX() {
		return x_coord;
	}

	public int getY() {
		return y_coord;
	}

	/**
	  * @return the type of character (i.e. its image file name)
	  */
	public abstract String getType();

	/**
	  * Draws the character to the window
	  * @param g 	The Graphics object to draw on
	  * @param step	The type of step that the character is taking
	  */
	public void draw(Graphics g) {
		stepNo %= TOTAL_STEPS;
		g.drawImage(getSprite(stepNo), x_coord, y_coord % Utility.FRAME_HEIGHT, null);
	}
	
	/** 
	  * @param boundaries 	a list of Boundary objects that may be collided with
	  * @return whether the character has collided with a boundary object
	  */
	public boolean hasCollided(java.util.List<Boundary> boundaries) {
		for (Boundary bnd : boundaries) {
			 if (bnd.isColliding(this)) return true;
		}
		return false;
	}

	/**
	  * @return the name of the character
	  */
	public String toString() {
		return name;
	}

	/**
	  * Holds data of the character's last movement. Used to determine whether to repaint the character.
	  * 
	  * <p> 
	  * If the character's last movement was in a different direction or greater than the refresh rate, 
	  * then a character should be repainted. 
	  */
	protected class LastTrigger {
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

		public LastTrigger (long t, char d) {
			time = t;
			direction = d;
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
				// admittedly the values will be different but this is okay
				time = System.nanoTime();
				direction = currDir;
			}
			return shouldRefresh;
		}

	}
}