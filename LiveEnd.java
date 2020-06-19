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
	private final Map<String, Integer> failures; 

	/**
	  * The background
	  */
	private Image background;

	/**
	  * Text return button to return to the main menu
	  */
	private Button returnButton;

	/**
	  * Text left button to flip between "mistakes" / "failures"
	  */
	private Button leftButton;

	/**
	  * Text right button to flip between "mistakes" / "failures"
	  */
	private Button rightButton;

	/**
	  * The drawing to be displayed on the screen
	  */
	private EndDrawing drawing; 

	/**
	  * The current failure being viewed
	  */
	private int currentFailure; 

	private static final int failureYAlign; 

	private java.util.List<String> failureKeys;

	static {
		failureYAlign = 380;
	}

	/**
	  * Constructs a station
	  * @param inTraining 	whether the user is in a training level or not
	  */
	public LiveEnd(Map<String, Integer> failures) {
		this.failures = failures;

		background = Utility.loadImage("Dialogue_Box.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);

		String message = "Return to the Main Menu";
		returnButton = new Button(message, (Utility.FRAME_WIDTH - Utility.getStringWidth(message, Utility.LABEL_FONT))/2, Utility.FRAME_HEIGHT - 60, Utility.LABEL_FONT, Color.white, Utility.RED);

		int xDiff = 30;
		leftButton = new Button("<", Utility.FRAME_WIDTH/2 - xDiff, failureYAlign, Utility.TEXT_FONT, Color.white, Utility.RED);
		rightButton = new Button(">", Utility.FRAME_WIDTH/2 + xDiff, failureYAlign, Utility.TEXT_FONT, Color.white, Utility.RED);

		currentFailure = 0;
		failureKeys = new ArrayList<String>(failures.keySet());

		drawing = new EndDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * Activate the listeners of any components of the screen
	  */
	public void activate() {
		returnButton.activate();

		if (failures.size() > 1) {
			leftButton.activate();
			rightButton.activate();
		}
	}

	/**
	  * Deactivate the listeners of any components of the screen
	  */
	public void halt() {
		returnButton.deactivate();

		if (failures.size() > 1) {
			leftButton.deactivate();
			rightButton.deactivate();
		}
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

		private void viewFailures(Graphics g) {
			if (failures.size() > 1) {
				leftButton.draw(g);
				rightButton.draw(g);

				if (leftButton.isClicked()) {
					currentFailure--;

					leftButton.resetClicked();

					if (currentFailure < 0) currentFailure = failures.size()-1;
				} else if (rightButton.isClicked()) {
					currentFailure++;

					rightButton.resetClicked();
					if (currentFailure >= failures.size()) currentFailure = 0;
				}

				g.setColor(Color.white);
				g.setFont(Utility.TEXT_FONT_SMALL);

				String message = "Use the arrows provided to flip between the preventative measures you missed";
				g.drawString(message, (Utility.FRAME_WIDTH - Utility.getStringWidth(message, g))/2, 280);
			} 

			g.setFont(Utility.TEXT_FONT);

			String error = failureKeys.get(currentFailure);
			error += " (" + failures.get(error) + "x)";
			g.drawString(error, (Utility.FRAME_WIDTH - Utility.getStringWidth(error, g))/2, failureYAlign-50);
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

			String [] messages = {"You completed the simulation level, ", null, "measures to avoid spreading the virus"};
			if (failures.isEmpty()) {
				messages[1] = "and you properly maintained preventative";
			} else {
				messages[1] = "but you sometimes failed to maintain preventative";

			}
				viewFailures(g);

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
