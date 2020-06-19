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
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Customer(String name, char gender, String equipment) {
		super(name,"customer",gender,'C',equipment);
    }
    /**
	  * Loads the image files into the steps HashMap for each character subclass
	  */
	@Override
	protected void loadSprites() {
		String[][] keys = {{"S", "E", "N", "W"},
						{"1", "2", "3", "4"}};
		int[][][] coords = {{{112, 288}, {128, 240}, {112, 288}, {144, 240}},
							{{112, 288}, {128, 256}, {112, 288}, {128, 256}}};
		Image spritesheet = Utility.loadImage("Customer"+gender+".png",(int)(2048/4.8),(int)(2048/4.8));
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				String key = keys[0][y]+"-"+keys[1][x]+"-C-"+(gender=='M'?"N":"M");
				BufferedImage sprite = Utility.toBufferedImage(spritesheet)
						.getSubimage((int)(x*512/4.8+coords[(gender=='M'?0:1)][y][0]/4.8), (int)(y*512/4.8+16/4.8), (int)(coords[(gender=='M'?0:1)][y][1]/4.8), 100);
                steps.put(key, sprite);
            }
        }
	}

	/**
	  * @return the type of character (i.e. its image file name)
	  */
	@Override
	public String getType() {
		return "Customer" + gender;
	}
}