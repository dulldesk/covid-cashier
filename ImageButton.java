/**
  * Button with an image
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.1
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageButton extends Button {
	/**
	  * The button icon
	  */
	private Image icon;

	/**
	  * The button icon, enlarged. For hovering.
	  */
	private Image icon_enlarged;

	/**
	  * The difference in the icon dimensions between the regular size and the enlarged size.
	  * Prerequisite: the value is even. 
	  */
	private int enlargeDiff = 10;

	/**
	  * Constructs a Button object and activates its listeners
	  * @param lbl 		the Button's label (name)
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font`
	  * @param lbl_c	the Button's label (text) colour
	  * @param hvr_c	the Button's label (text) colour upon hovering
	  */
	public ImageButton(String lbl, int x, int y, Font fnt, Color lbl_c, Color hvr_c, Image img) {
		super(lbl,x,y,fnt,lbl_c,hvr_c);
		icon = img;
		icon_enlarged = img.getScaledInstance(img.getWidth(null)+enlargeDiff, img.getHeight(null)+enlargeDiff, java.awt.Image.SCALE_SMOOTH);
	}

	/**
	  * Constructs a Button object with default colours. 
	  * @param lbl 		the Button's label (name)
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param fnt 		the Button's font`
	  * @param lbl_c	the Button's label (text) colour
	  * @param hvr_c	the Button's label (text) colour upon hovering
	  * @param img		the Button's image
	  */
	public ImageButton(String lbl, int x, int y, Font fnt, Image img) {
		super(lbl,x,y,fnt);

		icon = img;
		icon_enlarged = img.getScaledInstance(img.getWidth(null)+enlargeDiff, img.getHeight(null)+enlargeDiff, java.awt.Image.SCALE_SMOOTH);
	}

	/**
	  * Constructs a Button object without a label
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param img		the Button's image
	  */
	public ImageButton(int x, int y, Image img) {
		super("",x,y,null);
		icon = img;
		icon_enlarged = img.getScaledInstance(img.getWidth(null)+enlargeDiff, img.getHeight(null)+enlargeDiff, java.awt.Image.SCALE_SMOOTH);
	}

	/**
	  * Gets the button's icon
	  * @return the image icon of the button
	  */
	public Image getIcon() {
		return icon; 
	}

	/**
	  * Draws the Button, changing the text colour if hovered over
	  * @param g 		the Graphics object to draw on
	  */
	@Override
	public void draw(Graphics g) {
	    try {
	    	if (isHovered) {
				g.drawImage(icon_enlarged,x_coord-enlargeDiff/2,y_coord-enlargeDiff/2,null);
				g.setFont(text_font.deriveFont(text_font.getSize()*1.1F));
	    	} else {
				g.drawImage(icon,x_coord,y_coord,null);
				g.setFont(text_font);
	    	}

	    	// if label exists, draw it
			if (!name.trim().equals("")) {
				g.setColor(LABEL_COLOUR);
				g.drawString(name,x_coord+icon.getWidth(null)/2-Utility.getStringWidth(name,g)/2,y_coord+icon.getHeight(null)+60);
			}
		} catch (Exception e) {}
	}

	/**
	  * Check whether the user's mouse is within the boundaries of this Button
	  * @return whether the mouse is within the boundaries of this Button
	  */
	@Override
	protected boolean withinCoordinates() {
		try {
			Point pnt = CovidCashier.frame.getMousePosition();

			if (text_font == null)
				return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord + icon.getWidth(null) && pnt.y < y_coord + icon.getHeight(null);
			return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord + Math.max(icon.getWidth(null),name.length()*text_font.getSize()/2)  && pnt.y < y_coord + icon.getHeight(null)+text_font.getSize();
		} catch (Exception e) {}
		return false;
	}
}
