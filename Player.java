/**
  * A player character
  * 
  * Last edit: 5/29/2020
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
	private Map<String,Movement> movement;

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);
 		movement = new HashMap<String, Movement>();
		loadMovement();
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
	public void activate() {
		for (String key : movement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,movement.get(key).getAction());
		}
	}

	/**
	  * Deactivates the key bindings
	  */
	public void deactivate() {
		for (String key : movement.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,null);
		}
	}

	/**
	  * Loads movements into the map of key bindings and into the main frame's input map
	  */
	private void loadMovement() {
		final int DELTA_DIST = 10;
		movement.put("player-up", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (lastMvTime.compareNow('N')) {
			    	direction = 'N';
			    	y_coord -= DELTA_DIST;
			    	refresh();
		    	}
		    }
		}));

		movement.put("player-down", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (lastMvTime.compareNow('S')) {
			    	direction = 'S';
			    	y_coord += DELTA_DIST;
			    	refresh();
		    	}
		    }
		}));

		movement.put("player-left", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (lastMvTime.compareNow('W')) {
			    	direction = 'W';
			    	x_coord -= DELTA_DIST;
			    	refresh();
		    	}
		    }
		}));

		movement.put("player-right", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (lastMvTime.compareNow('E')) {
			    	direction = 'E';
			    	x_coord += DELTA_DIST;
			    	refresh();
			    }
		    }
		}));

		// Loads the movements into the main frame's input map
		for (String key : movement.keySet()) {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movement.get(key).getKeyStroke(),key);
		}
	}

	private void refresh() {
		stepNo++;
    	CovidCashier.frame.repaint();
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
