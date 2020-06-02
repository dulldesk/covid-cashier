/**
  * A station in the Restaurant
  * 
  * <p>The station is "represented" as an imaginary rectangle that detects hover / click events 
  * 
  * Last edit: 6/1/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public class Station extends Boundary {
	// private StationDrawing drawing;

	/**
	  * Constructs a station
	  * @param name 	the name of the station
	  * @param x 		x-coordinate (top-left corner)
	  * @param y 		y-coordinate (top-left corner)
	  * @param w 		width of the station
	  * @param h 		height of the station 
	  */
	public Station(String name, int x, int y, int w, int h) {
		super(x,y,w,h);
		this.name = name;

		isClicked = false;
		isHovered = false;
	}

	@Override
	public void draw(Graphics g) {}

	/**
	  * Activates click and hover listeners. 
	  */
	public void activate() {
		super.activate(true,false);
	}

	/**
	  * Deativates click and hover listeners.
	  */
	public void deactivate() {
		super.deactivate(true,false);
	}
}
