/**
  * The instructions screen
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Quit extends Menu {
	/**
	  * Contains the wuit screen graphics / GUI
	  */
	private MenuDrawing drawing;

	/**
	  * Holds the buttons displayed on the screen
	  */
	private ArrayList<Button> buttons;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public Quit() {
		buttons = new ArrayList<Button>(2);

		buttons.add(new Button("yes",LEFTALIGN,titleY+(int)(1.5*Style.LABEL_FONT.getSize()),Style.LABEL_FONT));
		buttons.add(new Button("no",LEFTALIGN,titleY+(int)(2*1.5*Style.LABEL_FONT.getSize()),Style.LABEL_FONT));

		drawing = new QuitDrawing();
		Style.changeDrawing(drawing);
	}

	/**
	  * Removes the drawing
	  */ 
	public void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();
	}

	/**
	  * GUI for the quit screen
	  */
	public class QuitDrawing extends MenuDrawing {
		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public QuitDrawing() {
			super();
			for (Button btn : buttons) btn.activate();
		}

		/**
		  * GUI display of the quit screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			g.setColor(Color.black);
			g.setFont(Style.TITLE_FONT_SMALL);
			g.drawString("Sure to leave",LEFTALIGN,titleY);

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
					if (btn.getName().equals("no")) {
						halt();
						new MainMenu();
						return;
					} else {
						CovidCashier.frame.dispose();
						System.exit(0);
					}
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
