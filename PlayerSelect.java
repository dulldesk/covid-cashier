/**
  * The character selection screen
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

public class PlayerSelect extends Menu {
	/**
	  * Contains the main menu graphics / GUI
	  */
	private static MenuDrawing drawing;

	/**
	  * Holds the icons displayed on the screen
	  */
	private static ArrayList<ImageButton> icons;

	/**
	  * Initializes static fields. Fills the icons ArrayList with ImageButton objects
	  */
	static {
		int alignY = 220;
		icons = new ArrayList<ImageButton>(2);

		icons.add(new ImageButton("male",200,alignY,Style.LABEL_FONT,Style.loadImage("male_player.png",100,100)));
		icons.add(new ImageButton("female",Style.FRAME_WIDTH-300,alignY,Style.LABEL_FONT,Style.loadImage("female_player.png",100,100)));
	}

	/**
	  * Initializes and displays the drawing to the frame
	  */
	private PlayerSelect() {
		drawing = new SelectionDrawing();
		// drawing.setEnabled(true);
		// CovidCashier.frame.add(drawing);
		// CovidCashier.frame.setContentPane(drawing);
		Style.changeDrawing(drawing);
	}

	/**
	  * Runs the graphics
	  */
	public static void drive() {
		new PlayerSelect();
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public static void halt() {
		drawing.setEnabled(false);
		CovidCashier.frame.remove(drawing);
		for (Button btn : icons) btn.deactivate();
	}

	/**
	  * GUI for the instructions
	  */
	public class SelectionDrawing extends MenuDrawing {
		/**
		  * The y-coordinate of the title
		  */
		private final int titleY = 90;

		private final Color LIGHT_BLUE = new Color(233, 255, 251);

		private final Color RED = new Color(214, 0, 0);

		private String stage = "character";

		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public SelectionDrawing() {
			super();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			// background
			g.setColor(LIGHT_BLUE);
			g.fillRect(0,0,Style.FRAME_WIDTH,300);
			g.setColor(RED);
			g.fillRect(0,300,Style.FRAME_WIDTH,Style.FRAME_HEIGHT-300);

			g.setColor(Color.black);

			if (stage.equals("name")) nameInput();
			else if (stage.equals("character")) charInput();
		}

		private void charInput() {
			drawTitle(g,"Select your player");

			for (Button btn : icons) {
				btn.draw(g);
				if (btn.isClicked()) {
	// System.out.println(CovidCashier.frame.getContentPane());
					if (btn.getName().equals("male")) {
						User.selected = new Player(User.name,'m');
					} else {
						User.selected = new Player(User.name,'f');
					}
					halt();
					MainMenu.drive();
					return;
				} 
			}
		}

		private void nameInput() {

		}

		/**
		  * Returns the y-coordinate of the title
		  * @return the SelectionDrawing object's titleY attribute
		  */
		@Override
		public int getTitleY() {
			return titleY;
		}
	}
}
