/**
  * Text field input box for Swing Graphics.
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TextField extends GraphicComponent implements KeyListener {
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
	  * @return the textfield's text
	  */
	public String getText() {
		return text;
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
	@Override
	public void activate() {
    	CovidCashier.frame.addKeyListener(this);
	}

	/**
	  * Deativates the button's listeners. It will cease to listen for click and hover events.
	  */
	@Override
	public void deactivate() {
		// Catch nonexistent listener exceptions
		try {
	    	CovidCashier.frame.addKeyListener(this);
		} catch (Exception e) {}
	}

	/**
	  * Handles the key press event. Enters text into the text field
	  * @param e 	the event object and data
	  */
	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    char key = (char)keyCode;

	    if (keyCode == KeyEvent.VK_ENTER) {
	    	hasEntered = true;
	    } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
	    	text = text.substring(0,text.length()-1);
	    } else if (key < ' ' || key > '~' || CovidCashier.frame.getFontMetrics(text_font).charsWidth((text+(char)keyCode).toCharArray(),0,text.length()+1) > width-20) {
	    	// prevent repaint as it is redundant
	    	return;
	    } else {
	    	text += (char)keyCode;
	    }
		CovidCashier.frame.repaint();
	} 

	/**
	  * Handles the key type event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	  * Handles the key release event. Nothing is to occur
	  * @param e 	the event object and data
	  */
	@Override
	public void keyReleased(KeyEvent e) {}
}
