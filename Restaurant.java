/**
  * The Restaurant workplace. This is where both the training level and the live level take place
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.1
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
	public final static int MAP_HEIGHT = 1040;
	
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
	private Player user;

	/**
	  * Number of completed stations
	  */
	public int completedStations;

	/**
	  * List of enter-able stations
	  */
	public static ArrayList<Station> stations;

	/**
	  * Boundaries that the user cannot move beyond
	  */
	public static ArrayList<Boundary> boundaries;

	/**
	  * Introductory dialogue to appear on the training level
	  */
	private Dialogue intro;
	
	private TaskList stationList;

	private static final Image LONG_COUNTER;

	private static final Image FRONT_COUNTER;

	public static int topY;

	private static java.util.List<String> trainingStationNames;

	static {
		stations = new ArrayList<Station>();
		boundaries = new ArrayList<Boundary>();
		loadStations();
		loadBoundaries();
		MAP = Utility.loadImage("Restaurant.png",Utility.FRAME_WIDTH,MAP_HEIGHT);
		LONG_COUNTER = Utility.loadImage("Counter.png",590,90);
		FRONT_COUNTER = Utility.loadImage("Front Counter.png",640,97);

		TrainingLevel.loadInfoMap();
	}

	public Restaurant(boolean training) {
		inTraining = training;

		completedStations = 0;

		// initial position
		user = new Player(User.name, User.gender);
		user.setCoordinates(100,800);
		System.out.println(user);

		topY = -user.getY() + Utility.FRAME_HEIGHT/2 - user.height/2;

		workplace = new RestaurantDrawing();
		Utility.changeDrawing(workplace);
	}

	/**
	  * Loads stations into its ArrayList
	  */
	private static void loadStations() {
		trainingStationNames = new ArrayList<String>();

		try {
			BufferedReader br = Utility.getBufferedReader("stations.txt");

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) {
				if (nxt.startsWith("&")) {
					String [] tokens = br.readLine().split(",");
					String stnName = nxt.substring(1);

					if (!stnName.equalsIgnoreCase("exit"))
						trainingStationNames.add(stnName);

					stations.add(new Station(stnName, Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),tokens[4].charAt(0)));
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
				if (nxt.startsWith("#") || tokens.length != 5) continue;
				boundaries.add(new Boundary(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),tokens[4].charAt(0)));
			}
		} 
		catch (IOException e) {}
		catch (NumberFormatException e) {}
	}

	/**
	  * Halt restaurant component listeners
	  */
	public void halt() {
		if (intro != null) intro.deactivate();
		if (stationList != null) stationList.deactivate();

		for (Station stn : stations) stn.deactivate();
	}

	/**
	  * Activate restaurant component listeners
	  */
	public void activate() {
		stationList.activate();

		user.restaurantMovement.activate();
	}

	/**
	  * Gets the y coordinate relative to the frame as opposed to the map image
	  * @param y the coordinate to use
	  * @return the y coordinate relative to the frame 
	  */
	public static int getYRelativeToFrame(int y) {
		return y + topY;
	}

	public int getCompletedStationsNo() {
		return completedStations;
	}

	/**
	  * Increase the number of completed stations and mark a station as complete
	  * @param stationName the station that has been completed
	  */
	public void completeStation(String stationName) {
		if (inTraining) ((Checklist)stationList).completeTask(stationName);
		else ((OrderList)stationList).completeTask(((OrderList)stationList).pageNo, stationName);
		completedStations++;
	}

	public ArrayList<Boundary> getBoundaries() {
		return boundaries;
	}

	public Player getUser() {
		return user;
	}

	public RestaurantDrawing getDrawing() {
		return workplace;
	}

	private class RestaurantDrawing extends JComponent {

		/**
		  * Constructor
		  */
		public RestaurantDrawing() {
			super();

			// change to coworker if available
			if (inTraining) 
				intro = new Dialogue(TrainingLevel.getInfo("intro"), "PlayerM");

			stationList = inTraining ? new Checklist(trainingStationNames.toArray(new String[trainingStationNames.size()])) : new OrderList();

			activate();
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

			final int longCounterY = 335;
			final int frontCounterY = 542;
			final int diffBehindCounter = 100;

			boolean userDrawn = false;

			// if statements allow for the user to go behind the counter
			if (user.getY() < longCounterY && user.getY() > longCounterY - diffBehindCounter) {
				user.draw(g, true);
				userDrawn = true;
			}
			g.drawImage(LONG_COUNTER,210,getYRelativeToFrame(longCounterY),null);

			if (!userDrawn && user.getY() < frontCounterY && user.getY() > frontCounterY - diffBehindCounter) {
				user.draw(g, true);
				userDrawn = true;
			}
			g.drawImage(FRONT_COUNTER,0,getYRelativeToFrame(frontCounterY),null);

			if (!userDrawn) 
				user.draw(g, true);


			// System.out.println(user.getX() + " " + user.getY() + " ; " + getYRelativeToFrame(user.getY()));
			// System.out.println(topY);

			// nothing to draw normally
			// for (Boundary bnd : boundaries) bnd.draw(g);

			if (intro != null) {
				if (intro.canProceed()) {
					intro.deactivate();
					intro = null;
					repaint();
				} else {
					intro.activate();
					intro.draw(g);
				}
			} else {
				for (Station stn : stations) {
					if (stn.getName().equals("exit")) {
						// if (!inTraining) {
							// for now
							// new MainMenu();
							// return;

							// todo : implement order completion checking
							// continue;
						// } else {

						// }
					}

					stn.draw(g);
					if (stn.isEntered()) {
						halt();

						// Uses the current restaurant object
						CovidCashier.setPastRestaurant(Restaurant.this);
						stn.resetDialogue();

						String currStn = stn.getName().toLowerCase();

						if (currStn.equals("exit")) {
							new MainMenu();
							return;
						}
						if (inTraining) {
							if (!currStn.equals("exit")) user.checkHygiene(currStn);
							switch (currStn) {
								case "drop off counter":
									return;
								case "covid counter":
									new CovidCounter(inTraining);
									return;
								default:
									new TrainingLevel(stn.getName());
									return;
								// case "fridge":
								// 	new TrainingLevel("Fridge");
								// 	return;
								// case "front counter": 
								// 	new TrainingLevel("Front Counter");
								// 	return;
								// case "pick up counter":
								// 	new TrainingLevel("Pick Up");
								//	return;
							}
						} else {
							if (!currStn.equals("exit")) user.checkHygiene(currStn);

							switch (currStn) {
								case "fridge":
									new FridgeTiles(user);
									return;
								case "covid counter":
									new CovidCounter(inTraining);
									return;
								case "front counter": 
									return;
								case "pick up counter":
									return ;
								case "drop off counter":
									return;
							}
							/*
								where:
								------
								new CashRun(user);
							*/
						}
					}
				}
			}

			stationList.draw(g);
		}
	}
}