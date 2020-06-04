/**
  * A graphic component
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;

public abstract class GraphicComponent implements MouseMotionListener, MouseListener, KeyListener {
	/**
	  * The field's x-coordinate (top left corner)
	  */
	protected int x_coord;

	/**
	  * The field's y-coordinate (top left corner)
	  */
	protected int y_coord;

	/**
	  * The field's width
	  */
	protected int width;

	/**
	  * The field's height
	  */
	protected int height;

	/**
	  * The Font of the name, if drawn
	  */
	protected Font text_font;
	
	/**
	  * Whether the user just clicked the component
	  */
	protected boolean isClicked;

	/**
	  * Whether the user is hovering over the component
	  */
	protected boolean isHovered;

	/** 
	  * The name of the component
	  */
	protected String name;

	/**
	  * @return whether the component has just been clicked by the user
	  */
	public boolean isClicked() {
		return isClicked;
	}

	/**
	  * @return whether the user is hovering over the component
	  */
	public boolean isHovered() {
		return isHovered;
	}

	public String getName() {
		return name;
	}

	/**
	  * Draws the component
	  * @param g 		the Graphics object to draw on
	  */
	public abstract void draw(Graphics g);

	/**
	  * Activates the component's listeners and the like
	  * @param mouse 	whether to listen for mouse events
	  * @param key 	 	whether to listen for key events
	  */
	public void activate(boolean mouse, boolean key) {
		if (mouse) {
	    	CovidCashier.frame.addMouseListener(this);
	    	CovidCashier.frame.addMouseMotionListener(this);
		}
		if (key) {
	    	CovidCashier.frame.addKeyListener(this);
		}
	}

	/**
	  * Deativates the components's listeners and the like
	  * @param mouse 	whether to stop listening for mouse events
	  * @param key 	 	whether to stop listening for key events
	  */
	public void deactivate(boolean mouse, boolean key) {
		if (mouse) {
	    	CovidCashier.frame.removeMouseListener(this);
	    	CovidCashier.frame.removeMouseMotionListener(this);
		}
		if (key) {
	    	CovidCashier.frame.removeKeyListener(this);
		}
	}

	/**
	  * Handles the mouse click event
	  * @param e 	the event object and data
	  */
	public void mouseClicked(MouseEvent e) {
		isClicked = withinCoordinates();
		if (isClicked) CovidCashier.frame.repaint();
	}

	/**
	  * Handles the mouse move event
	  * @param e 	the event object and data
	  */
	public void mouseMoved(MouseEvent e) {
		boolean before = isHovered;
		isHovered = withinCoordinates();
		if (isHovered != before) CovidCashier.frame.repaint();
	}

	/**
	  * Check whether the user's mouse is within the boundaries of this Button
	  * @return whether the mouse is within the boundaries of this Button
	  */
	protected abstract boolean withinCoordinates();

	public void mouseExited(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
}
