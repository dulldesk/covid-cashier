/**
  * A boundary object in the Restaurant (e.g. furniture)
  *
  * <p>The boundary object is "represented" as an imaginary rectangle. The user cannot walk on top of it
  *
  * Last edit: 6/1/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Boundary extends GraphicComponent {
	/**
	  * The direction that the player must face to enter the station
	  */
	protected char requiredDir;

	/**
	  * Constructs a boundary object
	  * @param x 		x-coordinate (top-left corner)
	  * @param y 		y-coordinate (top-left corner)
	  * @param w 		width of the boundary object
	  * @param h 		height of the boundary object
	  * @param dir 		the direction that a character must face in order to hit the boundary
	  */
	public Boundary(int x, int y, int w, int h, char dir) {
		x_coord = x;
		y_coord = y;
		width = w;
		height = h;
		requiredDir = dir;
	}
	public Boundary(int x, int y, int w, int h) {
		x_coord = x;
		y_coord = y;
		width = w;
		height = h;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x_coord,Restaurant.getYRelativeToFrame(y_coord),width,height);
	}

	/**
	  * @param figure 	the Character to detect collision for
	  * @return whether a given Character has collided with this boundary object
	  */
	public boolean isColliding(Character figure) {
		return figure.getDirection() == requiredDir &&
			figure.getX() <= x_coord + width &&
			figure.getX() + Character.WIDTH >= x_coord &&
			figure.getY() <= y_coord + height &&
			figure.getY() + Character.HEIGHT >= y_coord;
	}

	@Override
	protected boolean withinCoordinates() {
		return false;
	}
}
