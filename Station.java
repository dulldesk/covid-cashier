/**
  * A station in the Restaurant
  * 
  * <p>The station is "represented" as an imaginary rectangle that detects hover / click events 
  * 
  * Last edit: 6/12/2020
  * @author 	Celeste
  * @version 	1.1
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public class Station extends Boundary {
	private Dialogue entryCard;

	private boolean deactivated;

	private boolean canTrain;

	/**
	  * Constructs a station
	  * @param name 	the name of the station
	  * @param x 		x-coordinate (top-left corner)
	  * @param y 		y-coordinate (top-left corner)
	  * @param w 		width of the station
	  * @param h 		height of the station 
	  * @param dir 		the direction that a character must face in order to enter the station
	  * @param canTrain	whether the station has training information
	  */
	public Station(String name, int x, int y, int w, int h, char dir, boolean canTrain) {
		super(x,y,w,h,dir);
		this.name = name.trim();
		this.canTrain = canTrain;

		deactivated = false;
		if(name.contains("Don't"))
			entryCard = new Dialogue(name, "Coworker");
		else
			entryCard = new Dialogue(name,"Enter", false);
	}

	@Override
	public void draw(Graphics g) {
		if (isColliding(CovidCashier.getPastRestaurant().getUser())) {
			entryCard.draw(g);
			if (deactivated) activate();
		} else if (!deactivated) {
			deactivate();
		}
	}

	public boolean isEntered() {
		return entryCard.canProceed();
	}

	public void activate() {
		entryCard.activate();
		deactivated = false;
	}

	public void deactivate() {
		entryCard.deactivate();
		deactivated = true;
	}

	public void resetDialogue() {
		entryCard = new Dialogue(name,"Enter", false);
	}

	public boolean canTrain() {
		return canTrain;
	}
}
