/**
  * The Restaurant workplace. This is where both the training level and the live level take place
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Restaurant {
	/**
	  * The map image
	  */
	private final Image MAP;

	/**
	  * The map height
	  */
	private final int MAP_HEIGHT = 900;
	
	/**
	  * Whether the user has selected the training level
	  */
	private boolean inTraining;

	private RestaurantDrawing workplace;

	public Character user;

	private int completedStations;

	public static ArrayList<Station> stations;

	public static ArrayList<Boundary> boundaries;

	static {
		stations = new ArrayList<Station>();
		boundaries = new ArrayList<Boundary>();
		loadStations();
		loadBoundaries();
	}

	public Restaurant(boolean training) {
		MAP = Utility.loadImage("map.png",Utility.FRAME_WIDTH,MAP_HEIGHT);
		inTraining = training;

		user = new Player(User.name, User.gender);
		completedStations = 0;

		// initial position
		user.setCoordinates(0,100);

		workplace = new RestaurantDrawing();
		Utility.changeDrawing(workplace);
	}

	private static void loadStations() {
		try {
			BufferedReader br = Style.getBufferedReader("stations.txt");

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) {
				String [] tokens = nxt.split(",");
				if (nxt.startsWith("#") || tokens.length != 5) continue;
				stations.add(new Station(Integerteger.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),tokens[4].charAt(0)));
			}
		} 
		catch (IOException e) {}
		catch (NumberFormatException e) {}
	}

	private static void loadBoundaries() {
		try {
			BufferedReader br = Style.getBufferedReader("boundaries.txt");

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) {
				String [] tokens = nxt.split(",");
				if (nxt.startsWith("#") || tokens.length != 4) continue;
				boundaries.add(new Boundary(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])));
			}
		} 
		catch (IOException e) {}
		catch (NumberFormatException e) {}
	}

	public void halt() {
		for (Station stn : stations) stn.deactivate();
	}

	public int getCompletedStationsNo() {
		return completedStations;
	}

	public void increaseCompletedStations() {
		completedStations++;
	}

	public ArrayList<Boundary> getBoundaries() {
		return boundaries;
	}

	private class RestaurantDrawing extends JComponent {
		/**
		  * Constructor
		  */
		public RestaurantDrawing() {
			super();

			for (Station stn : stations) stn.activate();
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// if (!hasCollided(furniture)) user.draw(g);
			
		}
	}
}
