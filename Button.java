/**
  * Clickable button for Swing Graphics.
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Button extends GraphicComponent {
	/**
	  * The button's text colour 
	  */
	protected final Color LABEL_COLOUR;

	/**
	  * The button's text colour upon hovering
	  */
	protected final Color HOVER_COLOUR;

	/**
	  * Constructs a Button object and activates its listeners
	  * @param lbl 		the Button's label (name)
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font
	  * @param lbl_c	the Button's label (text) colour
	  * @param hvr_c	the Button's label (text) colour upon hovering
	  */
	public Button(String lbl, int x, int y, Font fnt, Color lbl_c, Color hvr_c) {
		name = lbl;
		x_coord = x;
		y_coord = y;
		text_font = fnt;
		LABEL_COLOUR = lbl_c;
		HOVER_COLOUR = hvr_c;
	}

	/**
	  * Constructs a Button object with default colours
	  * @param lbl 		the Button's name
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font`
	  */
	public Button(String lbl, int x, int y, Font fnt) {
		this(lbl, x, y, fnt, Color.black, Utility.RED);
	}

	/**
	  * Draws the Button, changing the text colour if hovered over
	  * @param g 		the Graphics object to draw on
	  */
	@Override
	public void draw(Graphics g) {
	    try { 
		   	g.setFont(text_font);
		   	g.setColor(isHovered ? HOVER_COLOUR : LABEL_COLOUR);
	   		g.drawString(name,x_coord,y_coord);
		} catch (Exception e) {}
	}

	/**
	  * Activates the button's listeners. It will listen for click and hover events.
	  */
	public void activate() {
		super.activate(true,false);
	}

	/**
	  * Deativates the button's listeners. It will cease to listen for click and hover events.
	  */
	public void deactivate() {
		super.deactivate(true,false);
	}

	/**
	  * Check whether the user's mouse is within the boundaries of this Button
	  * @return whether the mouse is within the boundaries of this Button
	  */
	@Override
	protected boolean withinCoordinates() {
		try {
			Point pnt = CovidCashier.frame.getMousePosition();
			return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord + name.length()*text_font.getSize()/2 && pnt.y < y_coord + text_font.getSize();
		} catch (Exception e) {}
		return false;
	}
}
