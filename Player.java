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
	Map<String,Movement> movement;

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
		String[][] keys = {{"s", "e", "n", "w"},
						{"1", "2", "3"},
						{"c", "w"},
						{"n", "m", "g", "mg"}};
		String[] imgs = {"C", "W", "W_M", "W_G", "W_MG"};
		for(int s = 0; s < 5; s++) {
			Image spritesheet = Style.loadImage("Player"+gender+"_"+imgs[s]+".png",(int)(1536/4.8),(int)(2048/4.8));
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 3; x++) {
					String key = keys[0][y]+"-"+keys[1][x]+"-"+(s==0?keys[2][0]:keys[2][1])+"-"+(s>1?keys[3][s-1]:keys[3][0]);
					BufferedImage sprite = Style.toBufferedImage(spritesheet).getSubimage((int)(x*512/4.8), (int)(y*512/4.8), (int)(512/4.8), (int)(512/4.8));
					steps.put(key, sprite);
				}
			}
		}
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
		movement.put("player-up", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'n';
		    	y_coord--;
		    	CovidCashier.frame.repaint();
		    }
		}));

		movement.put("player-down", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 's';
		    	y_coord++;
		    	CovidCashier.frame.repaint();
		    }
		}));

		movement.put("player-left", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'w';
		    	x_coord--;
		    	CovidCashier.frame.repaint();
		    }
		}));

		movement.put("player-right", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'e';
		    	x_coord++;
		    	CovidCashier.frame.repaint();
		    }
		}));

		// Loads the movements into the main frame's input map
		for (String key : movement.keySet()) {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movement.get(key).getKeyStroke(),key);
		}
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