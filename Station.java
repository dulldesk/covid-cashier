/**
  * A station in the Restaurant
  * 
  * <p>The station is "represented" as an imaginary rectangle that detects hover / click events 
  * 
  * Last edit: 6/3/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public class Station extends Boundary {
	private Dialogue entryCard;
	// private StationDrawing drawing;

	private boolean deactivated;

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
		this.name = name.trim();

		isClicked = false;
		isHovered = false;
		deactivated = false;

		entryCard = new Dialogue(name,"Enter", false);
	}

	@Override
	public void draw(Graphics g) {
		// g.drawRect(x_coord,Restaurant.getYRelativeToFrame(y_coord),width,height);
		if (isColliding(Restaurant.user)) {
			entryCard.draw(g);
			if (deactivated) activate();
		} else if (!deactivated) {
			deactivate();
		}
	}

	public boolean isEntered() {
		return entryCard.isEntered();
	}

	public void activate() {
		// super.activate(true,false);
		entryCard.activate();
		deactivated = false;
	}

	public void deactivate() {
		// super.deactivate(true,false);
		entryCard.deactivate();
		deactivated = true;
	}
}
