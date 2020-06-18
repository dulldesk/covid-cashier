/**
  * Return button
  *
  * <p>Purpose: eases the implementation of a button with a return arrow
  * 
  * Last edit: 6/17/2020
  * @author 	Celeste
  * @version 	1.1
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReturnButton extends ImageButton {
	/**
	  * Constructs a ReturnButton object 
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  * @param size 	the height and width of the return button image
	  */
	public ReturnButton(int x, int y, int size) {
		super(x, y, Utility.loadImage("ReturnArrow.png", size, size));
	}

	/**
	  * Constructs a ReturnButton object with default side length
	  * @param x 		the Button's x-coordinate
	  * @param y 		the Button's y-coordinate
	  */
	public ReturnButton(int x, int y) {
		this(x, y, 45);
	}
 }
