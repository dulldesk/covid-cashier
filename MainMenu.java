/**
  * The main menu screen
  * 
  * Last edit: 5/8/2020
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
	private MenuDrawing drawing;

	/**
	  * Holds the available options on the main menu
	  */
	private final String [] OPTIONS = {"Instructions", "Play", "Quit"};

	/**
	  * Holds the buttons displayed on the screen
	  */
	private ArrayList<Button> buttons;

	/**
	  *	The x-coordinate of the buttons
	  */
	private final int LEFTALIGN = 365;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public MainMenu() {
		buttons = new ArrayList<Button>(OPTIONS.length);

		for (int i=0;i<OPTIONS.length;i++) {
			buttons.add(new Button(OPTIONS[i],LEFTALIGN, (int)(193+(i*1.5)*Style.LABEL_FONT.getSize()), Style.LABEL_FONT));
		}
		for (Button btn : buttons) btn.activate();

		drawing = new MainMenuDrawing();
		Style.changeDrawing(drawing);
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
		  * The y-coordinate of the title
		  */
		private final int titleY = 140;

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
			// System.out.println("main menu");
			g.setFont(Style.TITLE_FONT_SMALL);
			g.setColor(Color.black);
			g.drawString("COVID CASHIER",LEFTALIGN,titleY);

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
					// do something
					switch (btn.getName()) {

					}
					// System.out.println("here "+btn.getName());
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
