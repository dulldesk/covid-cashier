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
	  * Whether the user has selected the training level
	  */
	private boolean inTraining;

	private RestaurantDrawing workplace;

	public Character user;

	public ArrayList<Station> stations;
	
	public Restaurant(boolean training) {
		MAP = Style.loadImage("map.png",Style.FRAME_WIDTH,Style.FRAME_HEIGHT);
		inTraining = training;

		user = new Player(User.name, User.gender);
		user.setCoordinates(0,100);

		stations = new ArrayList<Station>();
		loadStations();

		workplace = new RestaurantDrawing();
		Style.changeDrawing(workplace);
	}

	private void loadStations() {

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
			
			// user.draw(g);
			
		}
	}
}
