/**
  * The splash screen 
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SplashScreen {
	/**
	  * The logo
	  */
	private Image logo;

	/**
	  * The drawing to be displayed on the screen
	  */
	private SplashDrawing drawing; 

	/**
	  * The alpha (transparency) value of the logo
	  */
	private float logoAlpha;

	/**
	  * Timer to refresh the frame
	  */
	private javax.swing.Timer timer;

	/**
	  * Whether the alpha value of the logo is currently increasing
	  */
	private boolean alphaIncrease;

	/**
	  * The size of the logo
	  */
	private static final int LOGO_SIZE;

	static {
		LOGO_SIZE = 380;
	}

	/**
	  * Constructor
	  */
	public SplashScreen() {
		logo = Utility.loadImage("Logo.png", LOGO_SIZE, LOGO_SIZE);
		logoAlpha = 0;
		alphaIncrease = true;

		drawing = new SplashDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * The drawing of the splash screen
	  */
	private class SplashDrawing extends JComponent {
		/**
		  * Draw logo fade in and out
		  * @param g 	the Graphics object to draw on
		  */
		private void fadeInLogo(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(Math.min(1,Math.max(0,logoAlpha))));

            g2d.drawImage(logo, (Utility.FRAME_WIDTH-LOGO_SIZE)/2, (Utility.FRAME_HEIGHT-LOGO_SIZE)/2, null);

            g2d.dispose();

			logoAlpha += 0.1 * (alphaIncrease ? 1 : -1);

			if (logoAlpha > 1) {
				preloadImages();
				alphaIncrease = false;
			}
			refreshScreen();
		}

		/**
		  * Preload images to prevent future lag
		  */
		private void preloadImages() {
			Utility.loadImage("Restaurant.png", Utility.FRAME_WIDTH, Restaurant.MAP_HEIGHT);
			Utility.loadImage("Dialogue_Box.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
			Utility.loadImage("Dialogue_Box.png", Dialogue.BOX_WIDTH, Dialogue.BOX_HEIGHT);
			Utility.loadImage("Menu_BG.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
			Utility.loadImage("Cash_Register.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
			Utility.loadImage("Cash_Register.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);

			Player.maleSteps = Player.loadSprites('M');
			Player.femaleSteps = Player.loadSprites('F');
			Customer.maleSteps = Customer.loadSprites('M');
			Customer.femaleSteps = Customer.loadSprites('F');
			Coworker.staticSteps = Coworker.loadSprites('M');
		}

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.white);
			g.fillRect(0,0,Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);

			fadeInLogo(g);

			if (logoAlpha < -0.25) {
				new PlayerSelect();
			}
		}

		/**
		  * Refreshes the screen
		  */
		private void refreshScreen() {
			timer = new javax.swing.Timer(17, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(logoAlpha);
					repaint();
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
}
