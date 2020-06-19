/**
  * A Coworker character
  *
  * Last edit: 5/29/2020
  * @author 	  Eric
  * @version 	  1.0
  * @since 		  1.0
  */

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Coworker extends Character {
    /**
	    * Constructs a Character object and loads the appropriate sprites into the steps map
	    * @param name 	the Character's name, as chosen by the user
	    */
	  public Coworker(String name) {
        super(name,"coworker",'M','W',"MG");
    }

    /**
	    * Loads the image files into the steps HashMap for each character subclass
	    */
		@Override
		protected void loadSprites() {
        String[][] keys = {{"S", "E", "N", "W"},
                        {"1", "2", "3", "4"}};
        int[][] coords = {{112, 288}, {112, 352}, {112, 288}, {48, 352}};
        Image spritesheet = Utility.loadImage("Coworker.png",(int)(2048/4.8),(int)(2048/4.8));
        BufferedImage buffsheet = Utility.toBufferedImage(spritesheet);

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                String key = keys[0][y]+"-"+keys[1][x]+"-W-MG";
                BufferedImage sprite = buffsheet.getSubimage((int)(x*512/4.8+coords[y][0]/4.8), (int)(y*512/4.8+16/4.8), (int)(coords[y][1]/4.8), 100);
                steps.put(key, sprite);
	          }
	      }
		}

	/**
	  * @return the type of character (i.e. its image file name)
	  */
	@Override
	public String getType() {
		return "Coworker";
	}
}
