/**
  * The driver class. Runs the game
  * 
  * Last edit: 5/25/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.JFrame;
import java.awt.*;
import java.io.*;

public final class CovidCashier  {	
	/**
	  * The program frame
	  */
	public static JFrame frame;

	/**
	  * Initializes the main frame of the program an Image object from a file
	  */
	private void initializeFrame() {
		frame = new JFrame("Covid Cashier");
		frame.setSize(Style.FRAME_WIDTH,Style.FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	  * Constructs an object. The constructor will commence the game
	  */ 
	public CovidCashier() {
		initializeFrame();

		// new SplashScreen();
		new PlayerSelect();
		
		frame.setVisible(true);
	}

	/**
	  * The main method. Runs the program
	  * @param args 	Command-line arguments
	  */ 
	public static void main(String [] args) {
		new CovidCashier();
	}
}
