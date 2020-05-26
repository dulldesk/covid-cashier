/**
  * Stores data of and draws a monitor
  * 
  * Last edit: 5/25/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;

public class Monitor {
	/**
	  * The width of the monitor image, in pixels
	  */
	private static final int WIDTH = 400;

	/**
	  * The height of the monitor image, in pixels
	  */
	private static final int HEIGHT = 300;

	/**
	  * The x-coordinate of the monitor image (top-left corner)
	  */
	private static final int X_COORD = 100;

	/**
	  * The y-coordinate of the monitor image (top-left corner)
	  */
	private static final int Y_COORD = 100;

	/**
	  * The monitor image
	  * <p> With reference to:
	  * <ul>
	  *   <li> https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
	  *   <li> https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html#getScaledInstance(int,%20int,%20int)
	  *   <li> https://stackoverflow.com/questions/31127/java-swing-monitoring-images-from-within-a-jar
	  *   <li> https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
	  * </ul>
	  */
	private final static Image MONITOR = Style.loadImage("monitor.png",WIDTH,HEIGHT);

	/**
	  * Draws a Monitor to the window
	  * @param g 	The Graphics object to draw on
	  */
	public static void draw(Graphics g) {
		g.drawImage(MONITOR,X_COORD,Y_COORD,null);
	}
}