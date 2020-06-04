/**
  * A player character
  * 
  * Last edit: 6/3/2020
  * @author 	Celeste, Eric
  * @version 	1.1
  * @since 		1.0
  */

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Player extends Character {
	/**
	  * Contains key bindings for moving the player around
	  */
	private Map<String,Movement> restaurantMovement;

	/**
	  * Key bindings for player jumping
	  */
	private Movement cashRunMovement;

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);

		restaurantMovement = new HashMap<String, Movement>();
		loadRestaurantMovement();
		loadCashRunMovement();
	}

	/**
	  * Set player clothing
	  * @param clothing		the clothing type
	  */
	public void setClothing(char clothing) {
		clothingType = clothing;	
	}

	/**
	  * Set player clothing
	  * @param equipment	the protective equipment type
	  */
	public void setEquipment(String equipment) {
		protectiveEquipment = equipment;	
	}

	/**
	  * Loads the image files into the steps HashMap for each character subclass
	  */
	@Override
	protected void loadSprites() {
		String[][] keys = {{"S", "E", "N", "W"},
						{"1", "2", "3", "4"},
						{"C", "W"},
						{"N", "M", "G", "MG"}};
		String[] imgs = {"C", "W", "W_M", "W_G", "W_MG"};
		for(int s = 0; s < 5; s++) {
			Image spritesheet = Utility.loadImage("Player"+gender+"_"+imgs[s]+".png",(int)(2048/4.8),(int)(2048/4.8));
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					String key = keys[0][y]+"-"+keys[1][x]+"-"+(s==0?keys[2][0]:keys[2][1])+"-"+(s>1?keys[3][s-1]:keys[3][0]);
					BufferedImage sprite = Utility.toBufferedImage(spritesheet).getSubimage((int)(x*512/4.8), (int)(y*512/4.8), (int)(512/4.8), (int)(512/4.8));
					steps.put(key, sprite);
				}
			}
		}
	}

	/**
	  * @return the type of character (i.e. its image file name)
	  */
	@Override
	public String getType() {
		return "Player" + gender + "_" + clothingType + (protectiveEquipment.equals("N") ? "" : "_" + protectiveEquipment);
	}

	/**
	  * Activates the key bindings
	  */
	public void restaurantActivate() {
		for (String key : restaurantMovement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,restaurantMovement.get(key).getAction());
		}
	}

	/**
	  * Deactivates the key bindings
	  */
	public void restaurantDeactivate() {
		for (String key : restaurantMovement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,null);
		}
	}

	/**
	  * Activates the key bindings
	  */
	  public void cashRunActivate() {
		for (String key : restaurantMovement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,restaurantMovement.get(key).getAction());
		}
	}

	/**
	  * Deactivates the key bindings
	  */
	public void cashRunDeactivate() {
		for (String key : restaurantMovement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,null);
		}
	}

	/**
	  * Loads restaurant movements into the map of key bindings and into the main frame's input map
	  */
	private void loadRestaurantMovement() {
		final int DELTA_DIST = 10;

		final String [] keys = {"up", "down", "left", "right"};
		final char [] dirs = "NSWE".toCharArray();
		final int [] strokes = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};

		for (int i=0;i<keys.length;i++) {
			// resolves error: local variables referenced from an inner class must be final or effectively final
			final int index = i;

			restaurantMovement.put("player-"+keys[index], new Movement(KeyStroke.getKeyStroke(strokes[index], 0), new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if (lastMvTime.compareNow(dirs[index]) && !hasCollided(Restaurant.boundaries)) {
						direction = dirs[index];

						if (index < 2) y_coord += DELTA_DIST * (dirs[index] == 'N' ? -1 : 1);
						else x_coord += DELTA_DIST * (dirs[index] == 'W' ? -1 : 1);

						stepNo++;
						CovidCashier.frame.repaint();
					}
				}
			}));
		}

		// Loads the restaurant movements into the main frame's input map
		for (String key : restaurantMovement.keySet()) {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(restaurantMovement.get(key).getKeyStroke(),key);
		}
	}

	/**
	  * Loads cash run movements into the key binding
	  */
	private void loadCashRunMovement() {
		int dist = 10;
		cashRunMovement = new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				y_coord += dist;
				CovidCashier.frame.repaint();
			}
		});
	}
	/**
	  * Contains data about a key binding (i.e. the KeyStroke and Action)
	  */
	private class Movement {
		/**
		  * The KeyStroke to be mapped to the action
		  */
		private KeyStroke key;

		/**
		  * The action to be associated with the KeyStroke
		  */
		private AbstractAction action;

		/**
		  * Constructs a Movement object
		  */
		public Movement(KeyStroke k, AbstractAction a) {
			key = k;
			action = a;
		}

		/**
		  * Fetches the Action of the object
		  */
		public AbstractAction getAction() {
			return action;
		}

		/**
		  * Fetches the KeyStroke of the object
		  */
		public KeyStroke getKeyStroke() {
			return key;
		}
	}
}
