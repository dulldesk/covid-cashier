/**
  * A player character
  * 
  * Last edit: 6/18/2020
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
	public final RestaurantBindings restaurantMovement;

	/**
	  * Key bindings for player jumping
	  */
	public final CashRunBindings cashRunMovement;

	/**
	  * Contains key bindings for moving the player around
	  */
	public final FridgeTilesBindings fridgeTilesMovement;

	/**
	 * ---
	 */
	public boolean jumped;

	/**
	 * ---
	 */
	// public boolean activated;

	/**
	 * ---
	 */
	public int speed;

	/**
	  * Keeps track of the player's hygiene throughout the game
	  */
	public HygieneTracker hygienicTracker;

	/**
	  * Keeps track of the player's "failures" to prevent infection from the virus
	  */
	public Map<String, Integer> failures;

	/**
	  * The y-coordinate that divides the kitchen floor and the public space floor
	  */
	private static int KITCHEN_LINE = 595;

	/**
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);
		speed = 0;
		jumped = false;
		// activated = false;

		hygienicTracker = new HygieneTracker();
		failures = new HashMap<String, Integer>();

		restaurantMovement = new RestaurantBindings();
		fridgeTilesMovement = new FridgeTilesBindings();
		cashRunMovement = new CashRunBindings();
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
	  * Adds a mask to the player's PPE, if not already present
	  */
	public void putOnMask() {
		hygienicTracker.update("mask");
		if (protectiveEquipment.indexOf("M") == -1)  
			protectiveEquipment = "M" + protectiveEquipment.replace("N","");
	}

	/**
	  * Removes a mask from the player's PPE, if present
	  */
	public void takeOffMask() {
		if (protectiveEquipment.indexOf("M") != -1) {
			protectiveEquipment = protectiveEquipment.substring(1);
			if (protectiveEquipment.length() == 0) protectiveEquipment = "N";
		}
	}

	/**
	  * Adds gloves to the player's PPE, if not already present
	  */
	public void putOnGloves() {
		hygienicTracker.update("gloves");
		if (protectiveEquipment.indexOf("G") == -1) 
			protectiveEquipment = protectiveEquipment.replace("N","") + "G";
	}

	/**
	  * Removes gloves from the player's PPE, if present
	  */
	public void takeOffGloves() {
		protectiveEquipment = protectiveEquipment.charAt(0) == 'M' ? "M" : "N";
	}

	/**
	  * Cleans the users hands (i.e. updates the hand cleaning tracker)
	  */
	public void cleanHands() {
		hygienicTracker.update("clean hands");
	}

	/**
	  * Set player coordinates and change outfit as necessary
	  * @param x 	the x coordinate
	  * @param y 	the y coordinate
	  */
	public void setCoordinates(int x, int y) {
		super.setCoordinates(x, y);
		setClothing(y > KITCHEN_LINE ? 'C' : 'W');
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
		int[][][] coords = {{{112, 304}, {112, 288}, {96, 304}, {112, 288}},
		{{112, 304}, {112, 352}, {96, 304}, {48, 352}},
		{{112, 288}, {112, 288}, {112, 288}, {112, 288}},
		{{112, 288}, {112, 352}, {112, 288}, {48, 352}}};
		for(int s = 0; s < 5; s++) {
			Image spritesheet = Utility.loadImage("Player"+gender+"_"+imgs[s]+".png",(int)(2048/4.8),(int)(2048/4.8));
	        BufferedImage buffsheet = Utility.toBufferedImage(spritesheet);

			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					String key = keys[0][y]+"-"+keys[1][x]+"-"+(s==0?keys[2][0]:keys[2][1])+"-"+(s>1?keys[3][s-1]:keys[3][0]);
					BufferedImage sprite = buffsheet
					.getSubimage((int)(x*512/4.8+coords[(gender=='M'?0:2)+(s==0?0:1)][y][0]/4.8), (int)(y*512/4.8+16/4.8),
						(int)(coords[(gender=='M'?0:2)+(s==0?0:1)][y][1]/4.8), 100);
					steps.put(key, sprite);
				}
			}
		}
	}

	/** 
	  * Checks the user's hygiene and updates the tracker. 
	  * This method is intended to be called in between stations.
	  * @param nextStn 	the next station that the user is about to go to
	  */
	public void checkHygiene(String nextStn) {
		if (!nextStn.equals("covid counter")) {
			if (hygienicTracker.getLastTask("masks").equals("")) {
				addFailure("You did not wear a mask prior to a task");
			}

			if (!hygienicTracker.getLastTask().equals(nextStn) && !hygienicTracker.getLastTask("gloves").equals(hygienicTracker.getLastTask()) && !hygienicTracker.getLastTask("gloves").equals("covid counter")) {
				addFailure("You did not change your gloves in between tasks");
			}
			System.out.println("failures "+ failures.size());
		}

		hygienicTracker.setLastTask(nextStn);
	}

	public void addFailure(String failure) {
		failures.put(failure, failures.getOrDefault(failure, 0) + 1);
	}

	@Override
	public String getType() {
		return "Player" + gender + "_" + clothingType + (protectiveEquipment.equals("N") ? "" : "_" + protectiveEquipment);
	}

	/** 
	  * Keeps track of the frequency and/or presence of the player's hygienic practices
	  */
	private class HygieneTracker {
		/** 
		  * The last time, relative to the system time, that the player performed a "change" of some sort (the key, e.g. glove changing)
		  */
		private Map<String, Long> lastChangeTime; 

		/** 
		  * The last task performed prior to changing a PPE
		  */
		private Map<String, String> lastChangeTask; 

		/** 
		  * The last task that the user performed
		  */
		private String lastTask;

		public HygieneTracker() {
			lastChangeTime = new HashMap<String, Long>();
			lastChangeTask = new HashMap<String, String>();
			lastTask = "entry";
		}

		/** 
		  * Set the last change time of a given key to be the current moment
		  */
		public void update(String key) {
			System.out.println("update "+key);

			if (key.equals("gloves")) {
				if (!getLastTask("clean hands").equals(lastTask)) {
					addFailure("You did not sanitize your hands first before changing gloves");
					System.out.println("failures "+ failures.size());
				}
			}

			lastChangeTime.put(key, System.currentTimeMillis());
			lastChangeTask.put(key, lastTask);
		}

		/** 
		  * @return the last time a given key word was changed, or -1 if it was never changed
		  */
		public long getLastUpdate(String key) {
			return lastChangeTime.getOrDefault(key, -1l);
		}

		/** 
		  * @return the last task prior to a given key word was changed, or "" if it was never changed
		  */
		public String getLastTask(String key) {
			return lastChangeTask.getOrDefault(key, "");
		}

		public String getLastTask() {
			return lastTask;
		}

		public void setLastTask(String task) {
			lastTask = task;
		}
	}

	public class FridgeTilesBindings extends ScreenMovement {
		public FridgeTilesBindings() {
			super("fridge");
		}
		
		protected void loadKeyBindings() {
			movementMap.put("left", new Movement("left", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("left");
					if(x_coord-80 > 280)
						x_coord -= 80;
				}
			}));
			movementMap.put("right", new Movement("right", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("right");
					if(x_coord+80 < 520)
						x_coord += 80;
				}
			}));
		}
	}

	public class CashRunBindings extends ScreenMovement {
		public CashRunBindings() {
			super("cash");
		}

		protected void loadKeyBindings() {
			movementMap.put("jump", new Movement("jump", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					jumped = true;
					speed = 56;
				}
			}));
		}
	}

	public class RestaurantBindings extends ScreenMovement {
		public RestaurantBindings() {
			super("rest");
		}

		protected void loadKeyBindings() {
			final int DELTA_DIST = 10;

			final String [] keys = {"up", "down", "left", "right"};
			final char [] dirs = "NSWE".toCharArray();
			final int [] strokes = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};

			for (int i=0;i<keys.length;i++) {
				// resolves error: local variables referenced from an inner class must be final or effectively final
				final int index = i;


				movementMap.put(keys[index], new Movement(keys[index], KeyStroke.getKeyStroke(strokes[index], 0), new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						if (lastMvTime.compareNow(dirs[index])) {

							if (index < 2) y_coord += DELTA_DIST * (dirs[index] == 'N' ? -1 : 1);
							else x_coord += DELTA_DIST * (dirs[index] == 'W' ? -1 : 1);

							char origDir = direction;
							direction = dirs[index];

							if (hasCollided(Restaurant.boundaries)) {
	 							// undo
								if (index < 2) y_coord -= DELTA_DIST * (dirs[index] == 'N' ? -1 : 1);
								else x_coord -= DELTA_DIST * (dirs[index] == 'W' ? -1 : 1);
								
								direction = origDir;
								return;
							}

							if (index < 2) {
								setClothing(y_coord > KITCHEN_LINE ? 'C' : 'W');

								int changeY = Restaurant.topY - DELTA_DIST* (dirs[index] == 'N' ? -1 : 1);
								
								if ((index == 0 && changeY < 0 && y_coord < Restaurant.MAP_HEIGHT - Utility.FRAME_HEIGHT/2 - height/2) || 
									(index == 1 && y_coord > (Utility.FRAME_HEIGHT - height)/2 && changeY > -Restaurant.MAP_HEIGHT + Utility.FRAME_HEIGHT))
										Restaurant.topY = changeY;
							}

							stepNo++;
							CovidCashier.frame.repaint();
						}
					}
				}));
			}
		}
	}
}
