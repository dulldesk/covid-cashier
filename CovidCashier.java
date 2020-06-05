/**
  * The driver class. Runs the game
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
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
		frame.setSize(Utility.FRAME_WIDTH+6, Utility.FRAME_HEIGHT+29);
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
		//new CashRun('M', "MG");
		
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
