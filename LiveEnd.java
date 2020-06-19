/**
  * The screen that appears when the live level has been completed
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class LiveEnd {
	/**
	  * List of improper preventative measures employed to be revealed to the user
	  */
	private Map<String, Integer> failures; 

	/**
	  * The background
	  */
	private Image background;

	/**
	  * Text return button to return to the main menu
	  */
	private Button returnButton;


	/**
	  * The drawing to be displayed on the screen
	  */
	private EndDrawing drawing; 

	/**
	  * Constructs a station
	  * @param inTraining 	whether the user is in a training level or not
	  */
	public LiveEnd(Map<String, Integer> failures) {
		this.failures = failures;

		background = Utility.loadImage("Dialogue_Box.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);

		String message = "Return to the Main Menu";
		returnButton = new Button(message, (Utility.FRAME_WIDTH - Utility.getStringWidth(message, Utility.LABEL_FONT))/2, Utility.FRAME_HEIGHT - 60, Utility.LABEL_FONT, Color.white, Utility.RED);



		drawing = new EndDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * Activate the listeners of any components of the screen
	  */
	public void activate() {
		returnButton.activate();
	}

	/**
	  * Deactivate the listeners of any components of the screen
	  */
	public void halt() {
		returnButton.deactivate();
	}

	/**
	  * The drawing of a COVID Counter level screen
	  */
	private class EndDrawing extends JComponent {
		/**
		  * y-coordinate of the title
		  */
		private int titleY = 100;


		/**
		  * Constructor. Activates listeners
		  */
		public EndDrawing() {
			activate();
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(background,0,0,null);

			// title
			String title = "Level complete";
			g.setColor(Color.white);
			g.setFont(Utility.TITLE_FONT);
			g.drawString(title,(Utility.FRAME_WIDTH - Utility.getStringWidth(title, g))/2, titleY);

			String [] messages = {"You completed the simulation level, ", null, "to avoid spreading the virus"};
			if (failures.isEmpty()) {
				messages[1] = "and you properly maintained preventative measures";
			} else {
				messages[1] = "but you sometimes failed to maintain preventative measures";

			}

			g.setFont(Utility.TEXT_FONT);

			for (int i=0; i<messages.length;i++) {
				g.drawString(messages[i], (Utility.FRAME_WIDTH - Utility.getStringWidth(messages[i], g))/2, titleY + 50 + i*(Utility.LABEL_FONT.getSize() + 10));
			}

			returnButton.draw(g);


			if (returnButton.isClicked()) {
				halt();
				new MainMenu();
			}
		}
	}
}
