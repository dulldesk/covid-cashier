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
	  * The coworker
	  */
	private Coworker coworker;

	/**
	  * The customer
	  */
	private Customer customer;

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

	private static final Image LEFT_ARROW;

	public static int topY;

	private static java.util.List<String> trainingStationNames;

	public BGM bgm;

	static {
		stations = new ArrayList<Station>();
		boundaries = new ArrayList<Boundary>();
		loadStations();
		loadBoundaries();
		MAP = Utility.loadImage("Restaurant.png",Utility.FRAME_WIDTH,MAP_HEIGHT);
		LONG_COUNTER = Utility.loadImage("Counter.png",590,90);
		FRONT_COUNTER = Utility.loadImage("Front Counter.png",640,97);
		LEFT_ARROW = Utility.loadImage("LeftArrow.png",72,50);

		TrainingLevel.loadInfoMap();
	}

	public Restaurant(boolean training) {
		inTraining = training;

		completedStations = 0;

		// initial position
		user = new Player(User.name, User.gender);
		user.setCoordinates(100,800);
		coworker = new Coworker("Kym");
		coworker.setCoordinates(600, 250);
		int randCustomer = (int)(Math.random()*2);
		customer = new Customer((randCustomer==0?"Will":"Lauren"), (randCustomer==0?'M':'F'), (randCustomer==0?"N":"M"));
		System.out.println(user);

		topY = -user.getY() + Utility.FRAME_HEIGHT/2 - user.height/2;

		workplace = new RestaurantDrawing();
		Utility.changeDrawing(workplace);
		bgm = new BGM("restaurant");
		bgm.play();
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

					if (stnName.startsWith("*")) {
						stnName = stnName.substring(1);
					} else {
						trainingStationNames.add(stnName);
					}

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

	/**
	  * @return whether the list of orders / stations to be visited has been completed
	  */
	private boolean listIsCompleted() {
		return (inTraining ? ((Checklist)stationList) : ((OrderList)stationList)).isFinished();
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
				intro = new Dialogue(TrainingLevel.getInfo("intro"), "Coworker");

			stationList = inTraining ? new Checklist(trainingStationNames.toArray(new String[trainingStationNames.size()])) : new OrderList();

			activate();
		}

		private void drawInLayeringOrder(Graphics g) {
			final int longCounterY = 335;
			final int frontCounterY = 542;
			final int diffBehindCounter = 100;

			boolean userDrawn = false;

			coworker.draw(g, true);

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


			if (inTraining && User.hasTrained) {
				g.drawImage(LEFT_ARROW, 10, getYRelativeToFrame(470), null);
			}

			// Draw the counters and characters based on the layering order
			drawInLayeringOrder(g);

			// check if user has fully trained
			if (inTraining && !User.hasTrained && listIsCompleted()) {
				User.hasTrained = true;
				intro = new Dialogue("You've finished the training! Go to the arrow (and face in that direction) to exit the restaurant");
				repaint();
			}

			// check whether the live level has been completed
			if (!inTraining && listIsCompleted()) {
				halt();
				bgm.stop();
				new LiveEnd(user.failures);
				return;
			}

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
					if (stn.getName().equalsIgnoreCase("exit")) {
						if (!inTraining) {

							// todo : implement order completion checking
							// continue;
						} else if (!User.hasTrained) {
							// do not allow exiting
							continue;
						} 
					}

					stn.draw(g);
					if (stn.isEntered()) {
						halt();

						// Uses the current restaurant object
						CovidCashier.setPastRestaurant(Restaurant.this);
						stn.resetDialogue();

						String currStn = stn.getName().toLowerCase();
						bgm.pause();
						if (currStn.equals("exit")) {
							new MainMenu();
							return;
						}
						if (inTraining) {
							user.checkHygiene(currStn);
							completeStation(stn.getName());

							switch (currStn) {
								case "covid counter":
									new CovidCounter(inTraining);
									return;
								default:
									new TrainingLevel(stn.getName());
									return;
							}
						} else {
							user.checkHygiene(currStn);

							switch (currStn) {
								case "fridge":
									new FridgeTiles(user);
									return;
								case "covid counter":
									new CovidCounter(inTraining);
									return;
								case "front counter": 
									if(((OrderList)stationList).orders.get(0).tasks.get("Front Counter"))
										new CashRun(user);
									else
										new MemoryGame();
									return;
								case "long counter":
									new Disinfection();
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
