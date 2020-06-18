/**
  * The main menu screen
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.1
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
	private MenuDrawing drawing;

	/**
	  * Holds the available options on the main menu
	  */
	private final String [] OPTIONS = {"Instructions", "Train", "Play", "Quit"};

	/**
	  * Holds the buttons displayed on the screen
	  */
	private ArrayList<Button> buttons;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public MainMenu() {
		buttons = new ArrayList<Button>(OPTIONS.length);

		for (int i=0;i<OPTIONS.length;i++) {
			buttons.add(new Button(OPTIONS[i], leftAlign, titleY + 15 + (int)((i+1)*1.2*Utility.LABEL_FONT.getSize()), Utility.LABEL_FONT));
		}

		drawing = new MainMenuDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();
	}

	/**
	  * GUI for the instructions
	  */
	public class MainMenuDrawing extends MenuDrawing {
		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public MainMenuDrawing() {
			super();

			for (Button btn : buttons) btn.activate();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			// System.out.println("main menu");
			g.setFont(Utility.TITLE_FONT_SMALL);
			g.setColor(Color.black);
			centerAlignStr(g, "COVID Cashier", 545, titleY);

			drawReceipt(g);

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
					halt();
					switch (btn.getName().toUpperCase()) {
						case "INSTRUCTIONS": 
							new Instructions();
							break;
						case "TRAIN": 
							new Restaurant(true);
							break;
						case "PLAY": 
							new Restaurant(false);
							break;
						case "QUIT": 
							new Quit();
							break;
					}
					// System.out.println("here "+btn.getName());
					return;
				} 
			}
			refreshScreen();
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
