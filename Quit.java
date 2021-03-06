/**
  * The instructions screen
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Quit extends Menu {
	/**
	  * Contains the wuit screen graphics / GUI
	  */
	private MenuDrawing drawing;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public Quit() {
		loadButtons(new String[]{"yes", "no"});

		drawing = new QuitDrawing();
		Utility.changeDrawing(drawing);
		bgm = new BGM("menus");
		bgm.play();
	}

	/**
	  * Removes the drawing
	  */ 
	public void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();

		timer.stop();
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
			g.setFont(Utility.TITLE_FONT_SMALL);
			g.drawString("Are you sure",leftAlign,titleY);

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
					bgm.stop();
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

			drawReceipt(g);
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
