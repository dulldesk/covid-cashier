/**
  * The driver class. Runs the game
  *
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;

public final class CovidCashier  {
	/**
	  * The program frame
	  */
	public static JFrame frame;

	/**
	  * The past Restaurant level object
	  */
	private static Restaurant pastRestaurant;

	public static void setPastRestaurant(Restaurant currRestaurant) {
		pastRestaurant = currRestaurant;
	}

	public static Restaurant getPastRestaurant() {
		return pastRestaurant;
	}

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

		new SplashScreen();
		// new PlayerSelect();
		// new CashRun('M', "MG");
		//new FridgeTiles('M', "MG");
		//new Disinfection();
		// new MemoryGame();
		// new MainMenu();

		// setPastRestaurant(new Restaurant(true));

		// java.util.Map<String, Integer> a = new java.util.HashMap<String, Integer>();
		// a.put("You did not wear a mask prior to a task",10);
		// a.put("You did not change your gloves in between tasks",1);
		// a.put("You did not clean your hands before changing gloves",5);
		// new LiveEnd(a);

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
