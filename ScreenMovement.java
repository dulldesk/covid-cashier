/**
  * Contains key binding creation and usage methods
  * 
  * Last edit: 6/5/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.util.*;
import javax.swing.*;

public abstract class ScreenMovement {
	protected Map<String, Movement> movementMap;

	protected Movement movementStroke;

	protected boolean multiple;

	protected String prefix;

	public ScreenMovement(String pre) {
		prefix = pre;
		multiple = true;
		movementMap = new HashMap<String, Movement>();
		loadMovement();
	}

	public ScreenMovement(String pre, boolean single) {
		// must initialize the movement stroke in the loadKeyBindings method
		prefix = pre;
		multiple = false;
		movementStroke = null;
		loadMovement();
	}

	private void loadMovement() {
		loadKeyBindings();
		loadInputMap();
	}

	public void activate() {
		if (multiple) {
			for (String key : movementMap.keySet()) {
				CovidCashier.frame.getRootPane().getActionMap().put(movementMap.get(key).getName(),movementMap.get(key).getAction());
			}
		} else {
			CovidCashier.frame.getRootPane().getActionMap().put(movementStroke.getName(),movementStroke.getAction());
		}
	}

	public void deactivate() {
		if (multiple) {
			for (String key : movementMap.keySet()) {
				CovidCashier.frame.getRootPane().getActionMap().put(key,null);
			}
		} else {
			CovidCashier.frame.getRootPane().getActionMap().put(movementStroke.getName(),null);
		}
	}

	public abstract void loadKeyBindings();

	// Loads all bindings into the main frame's input map
	public void loadInputMap() {
		if (multiple) {
			for (String key : movementMap.keySet()) {
				CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movementMap.get(key).getKeyStroke(),movementMap.get(key).getName());
			}
		} else {
			CovidCashier.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(movementStroke.getKeyStroke(),movementStroke.getName());
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