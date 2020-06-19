/**
  * The COVID Counter Station in the Restaurant. Used for both the training and actual level
  * 
  * Last edit: 6/17/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;

public class CovidCounter extends TrainingLevel {
	/**
	  * Whether the user is in a training level
	  */
	private boolean inTraining;

	/**
	  * Interactive hand sanitizer
	  */
	private ImageButton sanitizer;

	/**
	  * Interactive box of gloves
	  */
	private ImageButton gloveBox;

	/**
	  * Interactive box of masks
	  */
	private ImageButton maskBox;

	/**
	  * ImageButton to exit the station
	  */
	private ImageButton returnButton; 

	/**
	  * Whether the instruction regarding how to interact with PPE is to be shown
	  */
	private boolean showInstruction; 

	/**
	  * The last millisecond that the change message showed up
	  */
	private long lastUpdate;

	/**
	  * The PPE that was last changed by the user
	  */
	private String lastChanged;

	/**
	  * Constructs a station
	  * @param inTraining 	whether the user is in a training level or not
	  */
	public CovidCounter(boolean inTraining) {
		super("COVID Counter", false);
		showInstruction = !inTraining;
		lastUpdate = 0;

		sanitizer = new ImageButton("sanitizer", 560, 175, Utility.TEXT_FONT, Utility.loadImage("Sanitizer.png", 100, 180), -21);
		maskBox = new ImageButton("masks", 320, 253, Utility.TEXT_FONT, Utility.loadImage("MaskBox.png", 160, 80));
		gloveBox = new ImageButton("gloves", 100, 250, Utility.TEXT_FONT, Utility.loadImage("GloveBox.png", 160, 84), -1);

		returnButton = new ReturnButton(50, Utility.FRAME_HEIGHT - 75);

		training = new CounterDrawing();
		Utility.changeDrawing(training);
	}

	/**
	  * Activate the listeners of any components of the level
	  * @param component whether the info placard or the dialogue should be activated
	  */
	public void activate(boolean components) {
		if (!components) super.activate();
		else {
			returnButton.activate();
			gloveBox.activate();
			maskBox.activate();
			sanitizer.activate();
		}
	}

	/**
	  * Deactivate the listeners of any components of the level
	  */
	public void halt() {
		super.deactivate();
		returnButton.deactivate();
		gloveBox.deactivate();
		maskBox.deactivate();
		sanitizer.deactivate();
	}

	/**
	  * The drawing of a COVID Counter level screen
	  */
	private class CounterDrawing extends JComponent {
		public CounterDrawing() {
			activate(showInstruction);
		}

		/** 
		  * Updates the last changed PPE
		  * @param btn 	the ImageButton of the selected PPE
		  */
		private void updatePPE(ImageButton btn) {
			lastUpdate = System.currentTimeMillis();
			lastChanged = btn.getName();
			btn.resetClicked();
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(background,0,0,null);
			sanitizer.draw(g);
			maskBox.draw(g);
			gloveBox.draw(g);
			returnButton.draw(g);

			if (!showInstruction) {
				if (infoCard != null && !infoCard.canProceed()) {
					infoCard.draw(g);
				} else {
					infoCard.deactivate();
					activate(true);
					showInstruction = true;
				}
			}

			if (showInstruction) {
				String instruction = "Click on a PPE to put it on";
				g.setColor(Color.black);
				g.setFont(Utility.TEXT_FONT);
				g.drawString(instruction, (Utility.FRAME_WIDTH - Utility.getStringWidth(instruction, g))/2, 100);

				String message = "Your current PPE: ";
				if (CovidCashier.getPastRestaurant().getUser().getPPE().startsWith("M")) message += "mask";
				if (CovidCashier.getPastRestaurant().getUser().getPPE().endsWith("G")) message += (CovidCashier.getPastRestaurant().getUser().getPPE().length() == 2 ? ", " : "") + "gloves";
				if (message.endsWith(" ")) message += "none";	

				g.drawString(message, (Utility.FRAME_WIDTH - Utility.getStringWidth(message, g))/2, 130);
			}

			if (maskBox.isClicked()) {
				CovidCashier.getPastRestaurant().getUser().putOnMask();
				
				SoundFX.play("gloveBox",300);
				updatePPE(maskBox);
			} else if (gloveBox.isClicked()) {
				CovidCashier.getPastRestaurant().getUser().putOnGloves();
				
				SoundFX.play("gloveBox",300);
				updatePPE(gloveBox);
			} else if (sanitizer.isClicked()) {
				CovidCashier.getPastRestaurant().getUser().cleanHands();
				
				SoundFX.play("sanitizer",300);
				updatePPE(sanitizer);
			} else if (returnButton.isClicked()) {
				halt();
				Utility.backToRestaurant();	
				return;	
			}

			if (lastUpdate >= System.currentTimeMillis() - 5000) {
				String message = "";

				if (lastChanged.equals("sanitizer")) message = "You cleaned your hands";
				else if (lastChanged.equals("gloves")) message = "You put on gloves";
				else if (lastChanged.equals("masks")) message = "You put on a mask";

				g.setColor(Color.black);
				g.setFont(Utility.TEXT_FONT_SMALL);
				g.drawString(message, (Utility.FRAME_WIDTH - Utility.getStringWidth(message, g))/2, Utility.FRAME_HEIGHT - 50);
			}
		}
	}
}
