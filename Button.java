/**
  * Clickable button for Swing Graphics.
  * 
  * Last edit: 5/25/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Button extends GraphicComponent implements MouseListener, MouseMotionListener {
	/** 
	  * The button label
	  */
	protected String label;

	/**
	  * Whether the user just clicked the button
	  */
	protected boolean isClicked;

	/**
	  * Whether the user is hovering over the button
	  */
	protected boolean isHovered;

	/**
	  * The button's text colour 
	  */
	protected final Color LABEL_COLOUR;

	/**
	  * The button's text colour upon hovering
	  */
	protected final Color HOVER_COLOUR;

	/**
	  * The button's text colour upon hovering
	  */
	protected final Color CLICK_COLOUR;

	/**
	  * Constructs a Button object and activates its listeners
	  * @param lbl 		the Button's label
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font`
	  * @param lbl_c	the Button's label (text) colour
	  * @param hvr_c	the Button's label (text) colour upon hovering
	  */
	public Button(String lbl, int x, int y, Font fnt, Color lbl_c, Color hvr_c, Color clk_c) {
		label = lbl;
		x_coord = x;
		y_coord = y;
		text_font = fnt;
		LABEL_COLOUR = lbl_c;
		HOVER_COLOUR = hvr_c;
		CLICK_COLOUR = clk_c;
		activate();
	}

	/**
	  * Constructs a Button object with default colours
	  * @param lbl 		the Button's label
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font`
	  */
	public Button(String lbl, int x, int y, Font fnt) {
		this(lbl,x,y,fnt,Color.black,Color.blue,Color.red);
	}

	/**
	  * Draws the Button, changing the text colour if hovered over
	  * @param g 		the Graphics object to draw on
	  */
	public void draw(Graphics g) {
	    try { 
		   	g.setFont(text_font);
		   	g.setColor(isHovered ? HOVER_COLOUR : (isClicked ? CLICK_COLOUR : LABEL_COLOUR));
	   		g.drawString(label,x_coord,y_coord);
		} catch (Exception e) {}
	}

	/**
	  * Returns whether the Button has just been clicked by the user
	  * @return whether the Button has just been clicked by the user
	  */
	public boolean isClicked() {
		return isClicked;
	}

	/**
	  * Returns the name of the Button (i.e. the label)
	  * @return the button's label
	  */
	public String getName() {
		return label;
	}

	/**
	  * Activates the button's listeners. It will listen for click and hover events.
	  */
	public void activate() {
    	CovidCashier.frame.addMouseListener(this);
    	CovidCashier.frame.addMouseMotionListener(this);
	}

	/**
	  * Deativates the button's listeners. It will cease to listen for click and hover events.
	  */
	public void deactivate() {
		// Catch nonexistent listener exceptions
		try {
	    	CovidCashier.frame.removeMouseListener(this);
	    	CovidCashier.frame.removeMouseMotionListener(this);		
		} catch (Exception e) {}
	}

	/**
	  * Handles the mouse click event
	  * @param e 	the event object and data
	  */
	public void mouseClicked(MouseEvent e) {
		isClicked = withinCoordinates();
		CovidCashier.frame.repaint();
	}

	/**
	  * Handles the mouse move event
	  * @param e 	the event object and data
	  */
	public void mouseMoved(MouseEvent e) {
		isHovered = withinCoordinates();
		CovidCashier.frame.repaint();
	}

	/**
	  * Check whether the user's mouse is within the boundaries of this Button
	  * @return whether the mouse is within the boundaries of this Button
	  */
	protected boolean withinCoordinates() {
		try {
			Point pnt = CovidCashier.frame.getMousePosition();
			return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord + label.length()*text_font.getSize()/2 && pnt.y < y_coord + text_font.getSize();
		} catch (Exception e) {}
		return false;
	}

	/**
	  * Handles the mouse exit event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	public void mouseExited(MouseEvent e) {}

	/**
	  * Handles the mouse enter event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	public void mouseEntered(MouseEvent e) {}

	/**
	  * Handles the mouse press event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	public void mousePressed(MouseEvent e) {}

	/**
	  * Handles the mouse release event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	public void mouseReleased(MouseEvent e) {}

	/**
	  * Handles the mouse drag event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	public void mouseDragged(MouseEvent e) {}
}
