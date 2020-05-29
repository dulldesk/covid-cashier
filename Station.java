/**
  * A station in the Restaurant
  * 
  * <p>The station is "represented" as an imaginary rectangle that detects hover / click events 
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public class Station extends GraphicComponent {
	/**
	  * Constructs a station
	  * @param name 	the name of the station
	  * @param x 		x-coordinate (top-left corner)
	  * @param y 		y-coordinate (top-left corner)
	  * @param w 		width of the station
	  * @param h 		height of the station 
	  */
	public Station(String name, int x, int y, int w, int h) {
		x_coord = x;
		y_coord = y;
		width = w;
		height = h;
		this.name = name;

		// no text font
		text_font = null;

		isClicked = false;
		isHovered = false;
	}

	@Override
	public void draw(Graphics g) {}

	/**
	  * Activates the station's listeners. It will listen for click and hover events.
	  */
	public void activate() {
		super.activate(true,false);
	}

	/**
	  * Deativates the station's listeners. It will cease to listen for click and hover events.
	  */
	public void deactivate() {
		super.deactivate(true,false);
	}

	/**
	  * Check whether the user's mouse is within the boundaries of this station
	  * @return whether the mouse is within the boundaries of this station
	  */
	@Override
	protected boolean withinCoordinates() {
		try {
			Point pnt = CovidCashier.frame.getMousePosition();
			return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord + width && pnt.y < y_coord + height;
		} catch (Exception e) {}
		return false;
	}
}
