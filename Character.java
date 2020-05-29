/**
  * A character, in the game
  * 
  * Last edit: 5/27/2020
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
	  * The character's gender, represented by its first character
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
	  * The type of clothing that the character currently wearing, denotated by the first character of said type. 
	  */
	protected char clothingType;

	/**
	  * The image sprites to be used for a character's movement.
	  * <p> The key is a String, formatted as: "d-s[-c-p]", where: 
	  * <ul>
	  *   <li> d is the direction that the character is facing (north, south, east, or west)
	  *   <li> s is the type of step that the character is taking (a natural number)
	  *   <li> c is the type of clothing to be drawn (casual or work). 
	  *   <li> p specifies the personal protective equipment that the player has (mask and/or gloves). Represented by the addition of a character (m, g, or mg). 
	  * </ul>
	  * <p> Optional parameters are denoted by [] in the key. 
	  * <p> The value of each key is the corresponding image represented by the key. 
	  */
	protected Map<String,Image> steps;

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
	protected static final int TOTAL_STEPS = 2;

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param type 	the name of the player sprite that was chosen
	  * @param gender 	the geneder of the Character chosen
	  */
	public Character(String name, String type, char gender) {
		this.name = name;
		this.gender = gender;

		// Default values
		this.direction = 'E'; 
		this.clothingType = 'c'; 

		// Starting position on the Restaurant map
		this.x_coord = 0;
		this.y_coord = 0;

		steps = new HashMap<String,Image>();
		loadSprites(type);
	}

	/**
	  * Loads the image files into the steps HashMap for each character subclass
	  * @param player 	the type of player
	  */
	protected abstract void loadSprites(String player);

	/**
	  * Get the sprite for the current step, given the character's other data as stored in the member variables
	  * @param step 	the step number. Prerequisite: a valid step; 0 <= step < TOTAL_STEPS
	  * @return the corresponding sprite for the current character's step movement
	  */
	protected Image getSprite(int step) {
		return null;
	}

	/**
	  * Draws the character to the window
	  * @param g 	The Graphics object to draw on
	  * @param step	The type of step that the character is taking
	  */
	public void draw(Graphics g, int step) {
		g.drawImage(getSprite(step%TOTAL_STEPS),x_coord,y_coord,null);
	}

	/**
	  * Provides the character's name
	  * @return the name of the character
	  */
	public String toString() {
		return name;
	}
}