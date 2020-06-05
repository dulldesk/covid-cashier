/**
  * A player character
  * 
  * Last edit: 6/5/2020
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

	public final RestaurantBindings restaurantMovement;
	/**
	  * Contains key bindings for moving the player around
	  */
	// private Map<String,Movement> restaurantMovement;

	/**
	  * Key bindings for player jumping
	  */
	// private Movement cashRunMovement;
	public final CashRunBindings cashRunMovement;

	/**
	  * Contains key bindings for moving the player around
	  */
	// private Map<String,Movement> fridgeTilesMovement;
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
	  * Constructs a Character object and loads the appropriate sprites into the steps map
	  * @param name 	the Character's name, as chosen by the user
	  * @param gender 	the gender of the Character chosen
	  */
	public Player(String name, char gender) {
		super(name,"player",gender);
		speed = 0;
		jumped = false;
		// activated = false;
		restaurantMovement = new RestaurantBindings();
		// restaurantMovement = new HashMap<String, Movement>();
		// fridgeTilesMovement = new HashMap<String, Movement>();
		fridgeTilesMovement = new FridgeTilesBindings();
		cashRunMovement = new CashRunBindings();

		// loadRestaurantMovement();
		// loadCashRunMovement();
		// loadFridgeTilesMovement();
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
		int[][][] coords = {{{112, 304}, {112, 288}, {96, 304}, {112, 288}},
		{{112, 304}, {112, 352}, {96, 304}, {48, 352}},
		{{112, 288}, {112, 288}, {112, 288}, {112, 288}},
		{{112, 288}, {112, 352}, {112, 288}, {48, 352}}};
		for(int s = 0; s < 5; s++) {
			Image spritesheet = Utility.loadImage("Player"+gender+"_"+imgs[s]+".png",(int)(2048/4.8),(int)(2048/4.8));
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					String key = keys[0][y]+"-"+keys[1][x]+"-"+(s==0?keys[2][0]:keys[2][1])+"-"+(s>1?keys[3][s-1]:keys[3][0]);
					BufferedImage sprite = Utility.toBufferedImage(spritesheet)
					.getSubimage((int)(x*512/4.8+coords[(gender=='M'?0:2)+(s==0?0:1)][y][0]/4.8), (int)(y*512/4.8+16/4.8),
						(int)(coords[(gender=='M'?0:2)+(s==0?0:1)][y][1]/4.8), 100);
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

	public void drawAtRestaurant(Graphics g) {
		stepNo %= TOTAL_STEPS;
		g.drawImage(getSprite(stepNo), x_coord, Restaurant.getYRelativeToFrame(y_coord), null);
	}

	public class FridgeTilesBindings extends ScreenMovement {
		public FridgeTilesBindings() {
			super("fridge");
		}
		
		public void loadKeyBindings() {
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
			super("cash", true);
		}

		public void loadKeyBindings() {
			movementStroke = new Movement("jump", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					jumped = true;
					speed = 52;
				}
			});
		}
	}

	public class RestaurantBindings extends ScreenMovement {
		public RestaurantBindings() {
			super("rest");
		}

		public void loadKeyBindings() {
			final int DELTA_DIST = 10;

			final String [] keys = {"up", "down", "left", "right"};
			final char [] dirs = "NSWE".toCharArray();
			final int [] strokes = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};

			for (int i=0;i<keys.length;i++) {
				// resolves error: local variables referenced from an inner class must be final or effectively final
				final int index = i;


				movementMap.put(keys[index], new Movement(keys[index], KeyStroke.getKeyStroke(strokes[index], 0), new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						System.out.println(dirs[index]);
						if (lastMvTime.compareNow(dirs[index])) {

							if (index < 2) y_coord += DELTA_DIST * (dirs[index] == 'N' ? -1 : 1);
							else x_coord += DELTA_DIST * (dirs[index] == 'W' ? -1 : 1);

							char origDir = direction;
							direction = dirs[index];

							if (false && hasCollided(Restaurant.boundaries)) {
							// if (hasCollided(Restaurant.boundaries)) {
	 							// undo
								if (index < 2) y_coord -= DELTA_DIST * (dirs[index] == 'N' ? -1 : 1);
								else x_coord -= DELTA_DIST * (dirs[index] == 'W' ? -1 : 1);
								
								direction = origDir;
								return;
							}
							if (index < 2) Restaurant.topY -= DELTA_DIST* (dirs[index] == 'N' ? -1 : 1);

							stepNo++;
							CovidCashier.frame.repaint();
						}
					}
				}));
			}
		}
	}
}
