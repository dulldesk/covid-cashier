/**
  * The character selection screen
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
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
	private MenuDrawing drawing;

	/**
	  * Holds the icons displayed on the screen
	  */
	private ArrayList<ImageButton> icons;

	/**
	  * A text field for name input
	  */
	private TextField field;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public PlayerSelect() {
		int alignY = 180;
		icons = new ArrayList<ImageButton>(2);

		icons.add(new ImageButton("male",200,alignY,Style.LABEL_FONT,Style.loadImage("PlayerM_Face.png",100,125)));
		icons.add(new ImageButton("female",Style.FRAME_WIDTH-300,alignY,Style.LABEL_FONT,Style.loadImage("PlayerF_Face.png",100,125)));

		field = new TextField(Style.FRAME_WIDTH/5,Style.FRAME_HEIGHT/2-25,3*Style.FRAME_WIDTH/5,Style.LABEL_FONT);

		drawing = new SelectionDrawing();
		Style.changeDrawing(drawing);
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public void halt() {
		drawing.setEnabled(false);
		CovidCashier.frame.remove(drawing);
		for (Button btn : icons) btn.deactivate();
	}

	/**
	  * GUI for the player selection
      * While not technically a part of the main menu, certain methods from MenuDrawing are required
      */
	public class SelectionDrawing extends MenuDrawing {
		/**
		  * The y-coordinate of the title
		  */
		private final int titleY = 90;

		/**
		  * The stage of selection
		  */
		private String stage;

		/**
	  	  * The menu image
		  */
		private final Image MENU = Style.loadImage("Menu_BG.png",Style.FRAME_WIDTH,Style.FRAME_HEIGHT);

		/**
	  	  * ---
		  */
		private Player testSprite;

		/**
		  * Object constructor. Uses the superclass's constructor and initializes fields.
		  */
		public SelectionDrawing() {
			super();
			field.activate();
			testSprite = new Player("A", 'F');
      
			// The first stage is name selection
			stage = "name";

			testSprite.activate();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			// background
			
			g.drawImage(MENU,0,0,null);

			g.setColor(Color.black);

			if (stage.equals("name")) nameInput(g);
			else if (stage.equals("character")) charInput(g);

			testSprite.draw(g);
			System.out.println(testSprite.getX()+" "+testSprite.getY());
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
					User.gender = btn.getName().charAt(0);

					halt();
					new MainMenu();
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
				

				for (Button btn : icons) btn.activate();
				field.deactivate();
			}

			// force refresh
			// repaint();
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
