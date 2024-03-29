/**
  * Contains and manages key bindings for specific screens (e.g. restaurant, a minigame)
  * 
  * Last edit: 6/5/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.util.*;
import javax.swing.*;

public abstract class ScreenMovement {
	/** 
	  * Maps keys to Movements (bindings) for a certain screen
	  */
	protected Map<String, Movement> movementMap;

	/** 
	  * Prefix of the key for the binding maps
	  * <p> Distinguishes keys for one screen movement from those for another
	  */
	protected String prefix;

	/**
	  * Constructor
	  * @param pre the prefix to be used
	  */
	public ScreenMovement(String pre) {
		prefix = pre;
		movementMap = new HashMap<String, Movement>();
		loadKeyBindings();
	}

	/**
	  * Activates the key bindings
	  */
	public void activate() {
		loadInputMap();
		for (String key : movementMap.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(movementMap.get(key).getName(),movementMap.get(key).getAction());
		}
	}

	/**
	  * Deactivates the key bindings
	  */
	public void deactivate() {
		unloadInputMap();
		for (String key : movementMap.keySet()) {
			CovidCashier.frame.getRootPane().getActionMap().put(key,null);
		}
	}

	/**
	  * Loads the key bindings for a particular screen
	  * <p> This process includes setting the action of what will occur upon a key binding being triggered
	  */
	protected abstract void loadKeyBindings();

	/**
	  * Loads all bindings into the main frame's input map
	  */
	protected void loadInputMap() {
		for (String key : movementMap.keySet()) {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movementMap.get(key).getKeyStroke(),movementMap.get(key).getName());
		}
	}

	/**
	  * Removes bindings into the main frame's input map
	  */
	protected void unloadInputMap() {
		for (String key : movementMap.keySet()) {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movementMap.get(key).getKeyStroke(),"none");
		}
	}

	/**
	  * Contains data about a key binding (i.e. the KeyStroke and Action)
	  */
	protected class Movement {
		/**
		  * The name of the KeyStroke
		  */
		private String name;

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
		public Movement(String n, KeyStroke k, AbstractAction a) {
			name = prefix+n;
			key = k;
			action = a;
		}
		
		/**
		  * Constructs a Movement object
		  */
		public Movement(String n, int k, AbstractAction a) {
			name = prefix+n;
			key = KeyStroke.getKeyStroke(k,0);
			action = a;
		}

		/**
		  * Fetches the name of the object
		  */
		public String getName() {
			return name;
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