/**
  * A menu screen
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public abstract class Menu {
	/**
	  *	The x-coordinate of the buttons
	  */
	protected int leftAlign = 365;

	/**
	  * The y-coordinate of the title
	  */
	protected int titleY = 130;

	/**
	  * Holds the buttons displayed on the screen
	  */
	protected ArrayList<Button> buttons;

	/**
	  * Deactivates any listeners on components on the screen
	  */
	public abstract void halt();

	public BGM bgm;

	/**
	 * Timer for the receipt refresh 
	 */
	public javax.swing.Timer timer;

	/**
	  * Loads buttons aligned to the screen of the cash register
	  * @param options	the options to be displayed
	  */
	protected void loadButtons(String [] options) {
		buttons = new ArrayList<Button>(options.length);

		for (int i=0;i<options.length;i++) {
			buttons.add(new Button(options[i], leftAlign, titleY + 5 + (int)((i+1)*1.2*Utility.LABEL_FONT.getSize()), Utility.LABEL_FONT));
		}		
	}
		
	/**
	  * GUI of a menu
	  */
	public abstract class MenuDrawing extends JComponent {
		/**
		 * Temporary order number
		 */
		protected int orderNumber = (int)(Math.random()*1000000);

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
		  * The background 
		  */
		private Image background;

		public MenuDrawing() {
			background = Utility.loadImage("Cash_Register.png", Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
		}

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
				g.drawImage(background, 0, 0, null);

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
		  * @param x 			the x-coordinate of the string
		  * @param y 			the y-coordinate of the string
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
		
		/**
		  * Draws a centre aligned String onto the given Graphics object
		  * @param g 			the Graphics object for the drawing
		  * @param phrase 		the String to be drawn
		  * @param x 			the x-coordinate of the middle of the string
		  * @param y 			the y-coordinate of the middle of the string
		  */
		public void centerAlignStr(Graphics g, String phrase, int x, int y) {
			g.drawString(phrase,x-Utility.getStringWidth(phrase,g)/2,y+g.getFontMetrics().getHeight()/2);
		}

		/**
		  * Draws text on the main menu receipt
		  * @param g 			the Graphics object for the drawing
		  */
		protected void drawReceipt(Graphics g) {
			Calendar date = Calendar.getInstance();
			String[] days = {"Sat", "Sun", "Mon", "Tues", "Wed", "Thurs", "Fri"};
			g.setColor(new Color(50, 50, 50));
			g.setFont(Utility.LABEL_FONT.deriveFont(18F));
			centerAlignStr(g, "Wandi's", 200, 82);
			g.setFont(Utility.LABEL_FONT.deriveFont(14F));
			centerAlignStr(g, "222 Corona St.", 190, 98);
			centerAlignStr(g, days[date.get(Calendar.DAY_OF_WEEK)]+" "+
			String.format("%02d",date.get(Calendar.MONTH)+1)+"/"+String.format("%02d",date.get(Calendar.DATE))+"/"+date.get(Calendar.YEAR)+" "+
			String.format("%02d",date.get(Calendar.HOUR))+":"+String.format("%02d",date.get(Calendar.MINUTE))+" "+
			(date.get(Calendar.AM_PM)==0?"AM":"PM"), 180, 116);
			centerAlignStr(g, "========================", 170, 132);
			centerAlignStr(g, "** ORDER#: "+String.format("%06d",orderNumber)+" **", 170, 148);
			g.drawString("1   PANDEMIC", 100, 180);
			g.drawString("1   PANDEMIC", 100, 200);
			g.drawString("SUBTOTAL", 100, 230);
			centerAlignStr(g, "========================", 165, 238);
			g.drawString("TOTAL", 100, 260);
		}

		/**
		  * Regularly refreshes the screen to update the receipt
		  */
		public void refreshScreen() {
			timer = new javax.swing.Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();
				}
			});
			timer.setRepeats(true);
			//Aprox. 60 FPS
			timer.setDelay(17);
			timer.start();
		}
	}
}
