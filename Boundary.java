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
	  * Constructs a boundary object
	  * @param x 		x-coordinate (top-left corner)
	  * @param y 		y-coordinate (top-left corner)
	  * @param w 		width of the boundary object
	  * @param h 		height of the boundary object 
	  */
	public Boundary(int x, int y, int w, int h) {
		x_coord = x;
		y_coord = y;
		width = w;
		height = h;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x_coord,Restaurant.getYRelativeToMap(y_coord),width,height);
	}

	/**
	  * @param figure 	the Character to detect collision for
	  * @return whether a given Character has collided with this boundary object
	  */
	public boolean isColliding(Character figure) {
		int y = Restaurant.getYRelativeToMap(y_coord);
		if (
			figure.getX() <= x_coord + width && 
			figure.getX() + Character.WIDTH >= x_coord && 
			figure.getY() <= y + height &&
			figure.getY() + Character.HEIGHT >= y
			) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean withinCoordinates() {
		return false;
	}
}
