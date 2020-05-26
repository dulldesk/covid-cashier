/**
  * The main menu screen
  * 
  * Last edit: 5/26/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MainMenu extends Menu {
	/**
	  * Contains the main menu graphics / GUI
	  */
	private static MenuDrawing drawing;

	/**
	  * Holds the available options on the main menu
	  */
	private static final String [] OPTIONS = {"Instructions", "Play", "Quit"};

	/**
	  * Holds the buttons displayed on the screen
	  */
	private static ArrayList<Button> buttons;

	/**
	  * Initializes static fields. Fills the buttons ArrayList with Button objects
	  */
	static {
		buttons = new ArrayList<Button>(OPTIONS.length);

		for (int i=0;i<OPTIONS.length;i++) {
			int fontSize = Style.LABEL_FONT.getSize();
			buttons.add(new Button(OPTIONS[i],Style.FRAME_WIDTH/2-fontSize*OPTIONS[i].length()/4, (int)((i+3)*1.55*fontSize), Style.LABEL_FONT));
		}
	}

	/**
	  * Initializes and displays the drawing to the frame
	  */
	private MainMenu() {
		drawing = new MainMenuDrawing();
		CovidCashier.frame.add(drawing);
	}

	/**
	  * Runs the graphics
	  */
	public static void drive() {
		new MainMenu();
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public static void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();
	}

	/**
	  * GUI for the instructions
	  */
	public class MainMenuDrawing extends MenuDrawing {
		/**
		  * The y-coordinate of the title
		  */
		private final int titleY = 90;

		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public MainMenuDrawing() {
			super();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			drawTitle(g,"COVID CASHIER");

			for (Button btn : buttons) {
				btn.paint(g);
				if (btn.isClicked()) {
					// do something
					System.out.println("here "+btn.getName());
				} 
			}
		}

		/**
		  * Returns the y-coordinate of the title
		  * @return the MainMenuDrawing object's titleY attribute
		  */
		@Override
		public int getTitleY() {
			return titleY;
		}
	}
}
