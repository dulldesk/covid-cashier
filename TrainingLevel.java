/**
  * A training level in the Restaurant
  * 
  * Last edit: 6/17/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class TrainingLevel {
	/**
	  * The training information to be delievered to the user
	  */
	protected Dialogue infoCard;

	/**
	  * Holds station to training level data
	  */
	public static Map<String,TrainingInfo> infoMap;

	/**
	  * The name of the station
	  */
	private String name;

	/**
	  * The dialogue text to be displayed at this station
	  */
	// private String text;

	/**
	  * The background image of this station
	  */
	protected Image background;

	/**
	  * The JComponent to be displayed on the window
	  */
	protected JComponent training;

	/**
	  * Constructs a training level station
	  * @param name 	the name of the station; should match the one in training.txt and stations.txt
	  */
	public TrainingLevel(String name) {
		this(name, false);

		training = new TrainingDrawing();
		Utility.changeDrawing(training);
	}

	/**
	  * Constructs a training level station but does not instantiate a TrainingDrawing
	  * @param name 	the name of the station; should match the one in training.txt and stations.txt
	  * @param noDraw 	extra boolean parameter to distinguish the two constructors
	  */
	public TrainingLevel(String name, boolean noDraw) {
		this.name = name.trim();

		if (infoMap.size() == 0) loadInfoMap();

		// text = infoMap.get(name).getInfo();
		background = infoMap.get(name).getBkgd();
		infoCard = new Dialogue(infoMap.get(name).getInfo(),"=",false);
	}

	/**
	  * Load the training level educational information into the infoMap HashMap
	  */
	public static void loadInfoMap() {
		infoMap = new HashMap<String,TrainingInfo>();
		try {
			BufferedReader br = Utility.getBufferedReader("training.txt");

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) {
				if (nxt.startsWith("&")) {
					String name = nxt.substring(1).trim();
					String info = "";

					nxt = br.readLine();

					for (; nxt != null && !nxt.trim().equals(""); nxt = br.readLine()) {
						info += nxt+"\n";
					}

					// require: background images
					// infoMap.put(name, new TrainingInfo(info.trim(), null));
					infoMap.put(name, new TrainingInfo(info.trim(), Utility.loadImage(name.replace(" ","") + "BG.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT)));
				}
			}
		} catch (Exception e) {}
	}

	/**
	  * Get the information associated with a station
	  * @param key 	the name of the station
	  * @return the training info for that station
	  */
	public static String getInfo(String key) {
		if (infoMap == null) loadInfoMap();
		return infoMap.get(key).getInfo();
	}

	/**
	  * @return whether the enter key has just been pressed
	  */
	public boolean isEntered() {
		return infoCard.isEntered();
	}

	/**
	  * Activate the listeners of any components of the level
	  */
	public void activate() {
		infoCard.activate();
	}

	/**
	  * Deactivate the listeners of any components of the level
	  */
	public void deactivate() {
		infoCard.deactivate();
	}

	/**
	  * The drawing of a training level screen
	  */
	protected class TrainingDrawing extends JComponent {
		/**
		  * Constructor; activates the components
		  */
		public TrainingDrawing() {
			activate();
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// temporary if statement
			if (background != null)
			g.drawImage(background,0,0,null);


			// Return to the restaurant once the training for that station has been completed
			// (i.e. all of the information has been read)
			if (infoCard.canProceed()) {
				deactivate();
				Utility.backToRestaurant();	
				return;	
			}

			// Draw the information (dialogue placard)
			infoCard.draw(g);
		}
	}

	/**
	  * Contains data about a training level
	  */
	private static class TrainingInfo {
		/**
		  * The text information to be "spoken"
		  */
		private String info;

		/**
		  * The background image of the level
		  */
		private Image bkgd;

		/**
		  * Constructs a TrainingInfo object
		  * @param info 	the information to be stored
		  * @param bkgd 	the Image to be used as the background
		  */
		public TrainingInfo(String info, Image bkgd) {
			this.info = info;
			this.bkgd = bkgd;
		}

		public String getInfo() {
			return info;
		}

		public Image getBkgd() {
			return bkgd;
		}
	}
}
