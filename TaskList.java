/**
  * A list of tasks for the user for both levels
  * 
  * Last edit: 6/4/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public abstract class TaskList extends GraphicComponent {
	/**
	  * Whether the list is opened or not
	  */
	protected boolean isOpened;

	/**
	  * The list icon
	  */
	protected ImageButton icon;

	/**
	  * The back button
	  */
	protected Button back;

	/**
	  * The list background
	  */
	protected Image bkgd;

	/**
	  * Whether the list has been added to at some point
	  */
	protected boolean notInitialEmpty;

	public TaskList() {
		this(true);

		bkgd = Utility.loadImage("Checklist.png",300,400);
		icon = new ImageButton(Utility.FRAME_WIDTH - 100, Utility.FRAME_HEIGHT - 100, Utility.loadImage("Scroll_Icon.png",80,80));
		back = new ImageButton(Utility.FRAME_WIDTH - 100, Utility.FRAME_HEIGHT - 75, Utility.loadImage("ReturnArrow.png",45,45));

		notInitialEmpty = false;

		isClicked = false;
		isHovered = false;
	}

	/**
	  * @param doNotLoad the addition of this parameters prevents image loading
	  */
	public TaskList(boolean doNotLoad) {
		width = 300;
		height = 400;
		x_coord = (Utility.FRAME_WIDTH-width)/2;
		y_coord = (Utility.FRAME_HEIGHT-height)/2;
	}

	@Override
	public void draw(Graphics g) {
		if (isOpened) {
			g.setColor(new Color(0,0,0,0.4f));
			g.fillRect(0,0,Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
			g.drawImage(bkgd,x_coord,y_coord,null);

			drawOpen(g);
			 
			back.draw(g);

			if (back.isClicked()) {
				isOpened = false;

				closeDeactivate();

				back.deactivate();
				icon.activate();
				CovidCashier.frame.repaint();
			}
		} else {
			icon.draw(g);

			if (icon.isClicked()) {
				isOpened = true;

				openActivate();

				icon.deactivate();
				back.activate();
				CovidCashier.frame.repaint();
			}
		}
	}

	/** 
	  * Draws an opened task list (i.e. displays the tasks on screen)
	  * @param g 	the Graphics object to draw on
	  */
	protected abstract void drawOpen(Graphics g);

	/** 
	  * @return whether the entire TaskList has been completed
	  */
	public abstract boolean isFinished();

	/** 
	  * Activate list opening listeners
	  */
	protected void openActivate() {}

	/** 
	  * Deactivate list opening listeners
	  */
	protected void closeDeactivate() {}

	/** 
	  * @return whether the list display is opened
	  */
	public boolean isOpened() {
		return isOpened;
	}

	/** 
	  * Closes the list display
	  */
	public void close() {
		isOpened = false;
	}

	/** 
	  * Opens the list display
	  */
	public void open() {
		isOpened = true;
	}

	/**
	  * Activates click and hover listeners. 
	  */
	public void activate() {
		icon.activate();
	}

	/**
	  * Deativates click and hover listeners.
	  */
	public void deactivate() {
		icon.deactivate();
		back.deactivate();
	}

	@Override
	protected boolean withinCoordinates() {return false;}
}
