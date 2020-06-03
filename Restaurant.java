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

	private ArrayList<Station> stations;

	private ArrayList<Boundary> furniture;

	
	public Restaurant(boolean training) {
		MAP = Utility.loadImage("map.png",Utility.FRAME_WIDTH,MAP_HEIGHT);
		inTraining = training;

		user = new Player(User.name, User.gender);

		// initial position
		user.setCoordinates(0,100);

		stations = new ArrayList<Station>();
		furniture = new ArrayList<Boundary>();
		loadStations();
		loadFurnitureBoundaries();

		workplace = new RestaurantDrawing();
		Utility.changeDrawing(workplace);
	}

	private void loadStations() {

	}

	private void loadFurnitureBoundaries() {

	}

	public void halt() {
		for (Station stn : stations) stn.deactivate();
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
