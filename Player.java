/**
  * A player character
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste
  * @version 	1.1
  * @since 		1.0
  */

import java.util.*;
import java.awt.*;
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
	  * @param gender 	the geneder of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);
 		movement = new HashMap<String, Movement>();
		loadMovement();
	}

	/**
	  * Loads the image files into the steps HashMap for each character subclass
	  * @param player 	the type of player
	  */
	@Override
	protected void loadSprites(String player) {
		
	}

	/**
	  * Activates the key bindings
	  */
	public void activate() {
		for (String key : movement.keySet()) {
			((JComponent)(CovidCashier.frame.getContentPane())).getActionMap().put(key,movement.get(key).getAction());
		}
	}

	/**
	  * Deactivates the key bindings
	  */
	public void deactivate() {
		for (String key : movement.keySet()) {
			((JComponent)(CovidCashier.frame.getContentPane())).getActionMap().put(key,null);
		}
	}

	/**
	  * Loads movements into the map of key bindings and into the main frame's input map
	  */
	private void loadMovement() {
		movement.put("player-up", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'N';
		    	y_coord++;
		    }
		}));

		movement.put("player-down", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'S';
		    	y_coord--;
		    }
		}));

		movement.put("player-left", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'W';
		    	x_coord--;
		    }
		}));

		movement.put("player-right", new Movement(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	direction = 'E';
		    	x_coord++;
		    }
		}));

		// Loads the movements into the main frame's input map
		for (String key : movement.keySet()) {
			((JComponent)(CovidCashier.frame.getContentPane())).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movement.get(key).getKeyStroke(),key);
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