/**
  * A menu screen
  * 
  * Last edit: 5/26/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public abstract class Menu {
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
		public void paint(Graphics g) {
			try { 
		   		// Background monitor
				Monitor.draw(g);

				// Draw the display
				display(g);

				// Repaint the display after 50 ms
				Thread.sleep(50);
				repaint();
			} catch (Exception e) {}
		}

		/**
		  * Draw the title / main heading
		  * @param g 	the Graphics object to draw on
		  */
		public void drawTitle(Graphics g, String title) {
			g.setFont(Style.TITLE_FONT);
			centerAlignStr(g,title,-0.2,g.getFont().getSize()+25);			
		}

		/**
		  * Draws a centre aligned String onto the given Graphics object
		  * @param g 			the Graphics object for the drawing
		  * @param phrase 		the String to be drawn
		  * @param row 			the row of the option
		  * @param fontSize 	the font size (or relative font size)
		  */
		public void centerAlignStr(Graphics g, String phrase, double row, int fontSize) {
			g.drawString(phrase,Style.FRAME_WIDTH/2-fontSize*phrase.length()/4,getTitleY() + (int)((row*1.55+0.5)*fontSize));
		}
	}
}
