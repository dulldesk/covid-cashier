/**
  * A player character
  * 
  * Last edit: 5/29/2020
  * @author 	Eric
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
	public Customer(String name, char gender) {
		super(name,"customer",gender);
		if(gender=='F')
			setEquipment("m");
    }
    /**
	  * Loads the image files into the steps HashMap for each character subclass
	  */
	@Override
	protected void loadSprites() {
		String[][] keys = {{"s", "e", "n", "w"},
						{"1", "2", "3"}};
		Image spritesheet = Style.loadImage("Customer"+(gender=='M'?1:2)+".png",(int)(1536/4.8),(int)(2048/4.8));
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 3; x++) {
				String key = keys[0][y]+"-"+keys[1][x]+"-c-"+(gender=='M'?"n":"m");
                BufferedImage sprite = Style.toBufferedImage(spritesheet).getSubimage((int)(x*512/4.8), (int)(y*512/4.8), (int)(512/4.8), (int)(512/4.8));
                steps.put(key, sprite);
            }
        }
	}
}