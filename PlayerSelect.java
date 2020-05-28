/**
  * The character selection screen
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

		/**
		  * Light blue colour of drawing
		  */
		private final Color LIGHT_BLUE = new Color(233, 255, 251);

		/**
		  * Red colour of drawing
		  */
		private final Color RED = new Color(214, 0, 0);

		/**
		  * The stage of selection
		  */
		private String stage;

		/**
		  * A text field for name input
		  */
		private TextField field;

		/**
		  * Object constructor. Uses the superclass's constructor and initializes fields.
		  */
		public SelectionDrawing() {
			super();
			field = new TextField(Style.FRAME_WIDTH/5,Style.FRAME_HEIGHT/2-25,3*Style.FRAME_WIDTH/5,Style.LABEL_FONT);

			// The first stage is name selection
			stage = "name";
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

			if (stage.equals("name")) nameInput(g);
			else if (stage.equals("character")) charInput(g);
		}

		/**
		  * Get character selection input
		  * @param g 	the Graphics object to draw on
		  */
		private void charInput(Graphics g) {
			drawTitle(g,"Select your player");

			// draw each player's icon
			for (Button btn : icons) {
				btn.draw(g);

				// if the icon has been clicked, get data and move on
				if (btn.isClicked()) {
					User.selected = new Player(User.name,btn.getName().charAt(0));

					halt();
					MainMenu.drive();
					return;
				} 
			}
		}

		/**
		  * Get the user's desired name
		  * @param g 	the Graphics object to draw on
		  */
		private void nameInput(Graphics g) {
			g.setColor(Color.black);
			drawTitle(g,"Enter your name");
			field.draw(g);

			// move on if the input is valid and the user has entered the text
			if (field.getText().length() > 0 && field.hasEntered()) {
				stage = "character";
				User.name = field.getText();

				field.deactivate();

				// refresh
				repaint();
			}
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
