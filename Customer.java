/**
  * A player character
  * 
  * Last edit: 6/18/2020
  * @author 	Eric, Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Customer extends Character {
 /**
      * Static map of a male player's steps. Used to cache loading
      */
	public static Map<String, Image> maleSteps;

    /**
      * Static map of a female player's steps. Used to cache loading
      */
	public static Map<String, Image> femaleSteps;

    /**
      * Whether the Customer has appeared yet
      */
	private boolean isPresent;

	static {
		maleSteps = new HashMap<String, Image>();
		femaleSteps = new HashMap<String, Image>();
	}

    /**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Customer(String name, char gender, String equipment) {
		super(name,"customer",gender,'C',equipment);
		isPresent = false;
    }

    /**
	  * Loads the image files into the steps HashMap for each character subclass
	  */
	@Override
	protected void loadSprites() {
		if (gender == 'M' && maleSteps.size() == 0) maleSteps = loadSprites('M');
		else if (gender == 'F' && femaleSteps.size() == 0) femaleSteps = loadSprites('F');
		steps = gender == 'M' ? maleSteps : femaleSteps;
	}

    /**
	  * Loads the image files into a static steps HashMap
	  * <p> Intended to be called for preloading
	  * @param gender the gender whose sprites are to be loaded
	  */
	public static Map<String, Image> loadSprites(char gender) {
		Map<String, Image> steps = new HashMap<String, Image>();

		String[][] keys = {{"S", "E", "N", "W"},
						{"1", "2", "3", "4"}};
		int[][][] coords = {{{112, 288}, {128, 240}, {112, 288}, {144, 240}},
							{{112, 288}, {128, 256}, {112, 288}, {128, 256}}};
		Image spritesheet = Utility.loadImage("Customer"+gender+".png",(int)(2048/4.8),(int)(2048/4.8));
        BufferedImage buffsheet = Utility.toBufferedImage(spritesheet);

		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				String key = keys[0][y]+"-"+keys[1][x]+"-C-"+(gender=='M'?"N":"M");
				BufferedImage sprite = buffsheet.getSubimage((int)(x*512/4.8+coords[(gender=='M'?0:1)][y][0]/4.8), (int)(y*512/4.8+16/4.8), (int)(coords[(gender=='M'?0:1)][y][1]/4.8), 100);
                steps.put(key, sprite);
            }
        }

        return steps;
	}

	/**
	  * @return the type of character (i.e. its image file name)
	  */
	@Override
	public String getType() {
		return "Customer" + gender;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresence(boolean present) {
		isPresent = present;
	}
}
