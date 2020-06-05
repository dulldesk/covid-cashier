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
	private static final Image MAP;

	/**
	  * The map height
	  */
	private final static int MAP_HEIGHT = 900;
	
	/**
	  * Whether the user has selected the training level
	  */
	private boolean inTraining;

	/**
	  * JComponent object of the workplace drawing
	  */
	private RestaurantDrawing workplace;

	/**
	  * The user's player
	  */
	public Player user;

	/**
	  * Number of completed stations
	  */
	private int completedStations;

	private TaskList stationList;

	/**
	  * List of enter-able stations
	  */
	public static ArrayList<Station> stations;

	/**
	  * Boundaries that the user cannot move beyond
	  */
	public static ArrayList<Boundary> boundaries;

	static {
		stations = new ArrayList<Station>();
		boundaries = new ArrayList<Boundary>();
		loadStations();
		loadBoundaries();
		MAP = Utility.loadImage("Restaurant.png",Utility.FRAME_WIDTH,MAP_HEIGHT);
	}

	public static int topY;

	public Restaurant(boolean training) {
		inTraining = training;

		completedStations = 0;
		topY = 0;

		// initial position
		user = new Player(User.name, User.gender);
		user.setCoordinates(0,375);

		workplace = new RestaurantDrawing();
		Utility.changeDrawing(workplace);
	}

	/**
	  * Loads stations into its ArrayList
	  */
	private static void loadStations() {
		try {
			BufferedReader br = Utility.getBufferedReader("stations.txt");

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) {
				if (nxt.startsWith("&")) {
					String [] tokens = br.readLine().split(",");
					stations.add(new Station(nxt.substring(1), Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),tokens[4].charAt(0)));
				}
			}
		} 
		catch (IOException e) {}
		catch (NumberFormatException e) {}
	}

	/**
	  * Loads boundaries into its ArrayList
	  */
	private static void loadBoundaries() {
		try {
			BufferedReader br = Utility.getBufferedReader("boundaries.txt");

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


	/**
	  * Gets the y coordinate relative to the frame as opposed to the map image
	  * @param y the coordinate to use
	  * @return the y coordinate relative to the frame 
	  */
	public static int getYRelativeToFrame(int y) {
		return y >= MAP_HEIGHT ? y - MAP_HEIGHT : y;
	}

	/**
	  * Gets the y coordinate relative to the map as opposed to the frame
	  * @param y the coordinate to use
	  * @return the y coordinate relative to the map 
	  */
	public static int getYRelativeToMap(int y) {
		return y + topY;
	}


	private class RestaurantDrawing extends JComponent {
		/**
		  * Constructor
		  */
		public RestaurantDrawing() {
			super();


			if (inTraining) {
				stationList = new Checklist();
			} else {
				stationList = new OrderList();
			}
			stationList.activate();

			for (Station stn : stations) stn.activate();
			user.restaurantActivate();
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// background
			g.drawImage(MAP,0,topY,null);

			user.draw(g);
			

			for (Boundary bnd : boundaries) bnd.draw(g);

			stationList.draw(g);
		}
	}
}
