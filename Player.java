/**
  * A plauer character
  * 
  * Last edit: 5/26/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Player extends Character /*implements KeyListener*/ {

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the geneder of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);
	}

	/**
	  * Loads the image files into the steps HashMap for each character subclass
	  * @param player 	the type of player
	  */
	protected void loadSprites(String player) {
		
	}
}