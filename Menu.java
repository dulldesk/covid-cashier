/**
  * A menu screen
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public abstract class Menu {
	/**
	  *	The x-coordinate of the buttons
	  */
	protected int leftAlign = 365;

	/**
	  * The y-coordinate of the title
	  */
	protected int titleY = 120;

	/**
	  * Deactivates any listeners on components on the screen
	  */
	public abstract void halt();
		
	/**
	  * GUI of a menu
	  */
	public abstract class MenuDrawing extends JComponent {

		/**
		  * Draw the actual display
		  * @param g 	the Graphics object to draw on
		  */
		public abstract void display(Graphics g);
		
		/**
		  * Get the y-coordinate of the title or main heading
		  * @return the y-coordinate of the title or main heading of the drawing
		  */
		public abstract int getTitleY();

		/**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// System.out.println("a "+CovidCashier.frame.getContentPane());
			try { 
		   		// Background monitor
				Monitor.draw(g);

				// Draw the display
				display(g);
			} catch (Exception e) {}
		}

		/**
		  * Draw the title / main heading
		  * @param g 			the Graphics object to draw on
		  * @param title		the phrase to be drawn
		  */
		public void drawTitle(Graphics g, String title) {
			g.setFont(Utility.TITLE_FONT);
			centerAlignStr(g,title,-0.2);			
		}

		/**
		  * Draw the title / main heading
		  * @param g 			the Graphics object to draw on
		  * @param title		the phrase to be drawn
		  * @param centerAlign	whether to center align the text
		  */
		public void drawTitle(Graphics g, String title, int x, int y) {
			g.setFont(Utility.TITLE_FONT);
			g.drawString(title,x,y);
		}

		/**
		  * Draws a centre aligned String onto the given Graphics object
		  * @param g 			the Graphics object for the drawing
		  * @param phrase 		the String to be drawn
		  * @param row 			the row of the option
		  */
		public void centerAlignStr(Graphics g, String phrase, double row) {
			g.drawString(phrase,Utility.FRAME_WIDTH/2-Utility.getStringWidth(phrase,g)/2,getTitleY() + (int)((row*1.55+0.5)*g.getFontMetrics().getHeight()));
		}
		
		public void centerAlignStr(Graphics g, String phrase, int x, int y) {
			g.drawString(phrase,x-Utility.getStringWidth(phrase,g)/2,y+g.getFontMetrics().getHeight()/2);
		}
	}
}
