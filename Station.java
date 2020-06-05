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
	  * @param dir 		the direction that a character must face in order to enter the station
	  */
	public Station(String name, int x, int y, int w, int h, char dir) {
		super(x,y,w,h,dir);
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

	/**
	  * Determines whether a given character is within the range of a station and facing it
	  * @param figure the figure whose proximity to the station is to be checked
	  * @return whether figure is within the range of a station
	  */
	public boolean withinStation(Character figure) {
		return figure.getDirection() == requiredDir 
			&& (requiredDir == 'N' || requiredDir == 'S' 
			? figure.getX() >= x_coord && figure.getX() + Character.WIDTH <= x_coord + width && ((requiredDir == 'N' && figure.getY() >= y_coord && figure.getY() <= y_coord + height) || (requiredDir == 's' && figure.getY() + Character.HEIGHT >= y_coord && figure.getY() + Character.HEIGHT <= y_coord + height))
			: figure.getY() >= y_coord && figure.getY() + Character.HEIGHT <= y_coord + height && ((requiredDir == 'W' && figure.getX() >= x_coord && figure.getX() <= x_coord + width) || (requiredDir == 'e' && figure.getX() + Character.WIDTH >= x_coord && figure.getX() + Character.WIDTH <= x_coord + width)) ); 
	}
}
