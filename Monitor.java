/**
  * Stores data of and draws a monitor
  * 
  * Last edit: 5/26/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;

public class Monitor {
	/**
	  * The width of the monitor image, in pixels
	  */
	private static final int WIDTH = Style.FRAME_WIDTH;

	/**
	  * The height of the monitor image, in pixels
	  */
	private static final int HEIGHT = Style.FRAME_HEIGHT;

	/**
	  * The x-coordinate of the monitor image (top-left corner)
	  */
	private static final int X_COORD = 0;

	/**
	  * The y-coordinate of the monitor image (top-left corner)
	  */
	private static final int Y_COORD = 0;

	/**
	  * The monitor image
	  */
	private final static Image MONITOR = Style.loadImage("Cash_Register.png",WIDTH,HEIGHT);

	/**
	  * Draws a Monitor to the window
	  * @param g 	The Graphics object to draw on
	  */
	public static void draw(Graphics g) {
		g.drawImage(MONITOR,X_COORD,Y_COORD,null);
	}
}