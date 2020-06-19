/**
  * The main menu screen
  * 
  * Last edit: 6/18/2020
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
	  * Dialogue message
	  */
	private Dialogue message;

	/**
	  * Holds the available options on the main menu
	  */
	private final String [] OPTIONS = {"Instructions", "Train", "Play", "Quit"};

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public MainMenu() {
		loadButtons(OPTIONS);
		message = new Dialogue(User.firstMainMenu ? "Click on any of the menu options to enter its screen. Press enter to close this dialogue box. " : "");

		drawing = new MainMenuDrawing();
		Utility.changeDrawing(drawing);
		bgm = new BGM("menus");
		bgm.play();
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();
		message.deactivate();
		User.firstMainMenu = false;
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
			message.activate();

			for (Button btn : buttons) btn.activate();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			g.setFont(Utility.TITLE_FONT_SMALL);
			g.setColor(Color.black);
			g.drawString("COVID Cashier", leftAlign, titleY);

			drawReceipt(g);

			if (!message.isEmpty() && !message.canProceed()) message.draw(g);
			else message.deactivate();

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
			    	btn.resetClicked();

					if (btn.getName().toUpperCase().equals("PLAY") && !User.hasTrained) {
						message = new Dialogue("You cannot enter the Live Level before undergoing training.");
						message.activate();
						repaint();
						continue;
					}

					halt();
					bgm.stop();
					switch (btn.getName().toUpperCase()) {
						case "INSTRUCTIONS":
							new Instructions();
							break;
						case "TRAIN": 
							CovidCashier.setPastRestaurant(new Restaurant(true));
							break;
						case "PLAY": 
							CovidCashier.setPastRestaurant(new Restaurant(false));
							break;
						case "QUIT": 
							new Quit();
							break;
					}
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
