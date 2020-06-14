/**
  * A training level in the Restaurant
  * 
  * Last edit: 6/12/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class TrainingLevel {
	private Dialogue infoCard;

	public static Map<String,TrainingInfo> infoMap;

	private String name;

	private String text;

	private Image background;

	private TrainingDrawing training;

	/**
	  * Constructs a station
	  * @param name 	the name of the station
	  */
	public TrainingLevel(String name) {
		this.name = name.trim();

		if (infoMap.size() == 0) loadInfoMap();

		text = infoMap.get(name).getInfo();
		background = infoMap.get(name).getBkgd();

		infoCard = new Dialogue(text,"=",false);


		training = new TrainingDrawing();
		Utility.changeDrawing(training);
	}

	public boolean isEntered() {
		return infoCard.isEntered();
	}

	public void activate() {
		infoCard.activate();
	}

	public void deactivate() {
		infoCard.deactivate();
	}

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

					// infoMap.put(name, new TrainingInfo(info.trim(), Utility.loadImage(name+" Station.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT)));
					// require: background images
					infoMap.put(name, new TrainingInfo(info.trim(), null));
				}
			}
		} catch (Exception e) {}
	}

	public static String getInfo(String key) {
		if (infoMap == null) loadInfoMap();
		return infoMap.get(key).getInfo();
	}

	private class TrainingDrawing extends JComponent {
		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// g.drawImage(background,0,0,null);

			infoCard.draw(g);
			if (infoCard.canProceed()) {
				Utility.backToRestaurant();			
			}
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
