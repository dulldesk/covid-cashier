/**
  * Text field input box for Swing Graphics.
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TextField extends GraphicComponent {
	/**
	  * Whether the user has entered their text
	  */
	protected boolean hasEntered;

	/**
	  * The actual text of the textfield
	  */
	protected String text; 

	/**
	  * Constructs a TextField object and activates its listeners
	  * @param x 		the TextField's x-coordinate
	  * @param y 		the TextField's y-coordinate
	  * @param w 		the TextField's width
	  * @param fnt 		the TextField's font`
	  */
	public TextField(int x, int y, int w, Font fnt) {
		x_coord = x;
		y_coord = y;
		width = w;
		text_font = fnt;
		height = fnt.getSize()+10;
		text = "";
	}

	/**
	  * Draws the TextField, changing the text colour if hovered over
	  * @param g 		the Graphics object to draw on
	  */
	@Override
	public void draw(Graphics g) {
   		g.setColor(Color.white);
		g.fillRoundRect(x_coord,y_coord,width,height,10,10);
		g.setColor(Color.black);
		g.drawRoundRect(x_coord,y_coord,width,height,10,10);

	   	g.setFont(text_font);
	   	g.drawString(text+" ",x_coord+10,y_coord+text_font.getSize());
	}

	/**
	  * Get the text in the textfield
	  * @return the textfield's text, trimmed of whitespace
	  */
	public String getText() {
		return text.trim();
	}

	/**
	  * Returns whether the user just submitted the TextField input
	  * @return whether the user just submitted the TextField inputs
	  */
	public boolean hasEntered() {
		return hasEntered;
	}

	/**
	  * Activates the button's listeners. It will listen for keyboard events.
	  */
	public void activate() {
		super.activate(false, true);
	}

	/**
	  * Deativates the button's listeners. It will cease to listen for click and hover events.
	  */
	public void deactivate() {
		super.deactivate(false, true);
	}

	/**
	  * Handles the key press event. Enters text into the text field
	  * @param e 	the event object and data
	  */
	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    char key = ((char)keyCode+"").toLowerCase().charAt(0);

	    if (keyCode == KeyEvent.VK_ENTER) {
	    	hasEntered = true;
	    } else if (text.length() > 0 && keyCode == KeyEvent.VK_BACK_SPACE) {
	    	text = text.substring(0,text.length()-1);
	    } else if (key == '%' || key == '\'' || key == '&' || key == '(' || key == ')' || key < ' ' || key > 'z' || CovidCashier.frame.getFontMetrics(text_font).charsWidth((text+(char)keyCode).toCharArray(),0,text.length()+1) > width-20) {
	    	// prevent repaint as it is redundant
	    	return;
	    } else {
	    	text += (char)keyCode;
	    }
		CovidCashier.frame.repaint();
	} 

	@Override
	protected boolean withinCoordinates() {return false;}
}
