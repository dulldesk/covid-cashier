/**
  * The main menu screen
  * 
  * Last edit: 5/29/2020
  * @author 	Celeste, Eric
  * @version 	1.1
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class MainMenu extends Menu {
	/**
	  * Contains the main menu graphics / GUI
	  */
	private MenuDrawing drawing;

	/**
	  * Holds the available options on the main menu
	  */
	private final String [] OPTIONS = {"Instructions", "Play", "Quit"};

	/**
	  * Holds the buttons displayed on the screen
	  */
	private ArrayList<Button> buttons;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public MainMenu() {
		buttons = new ArrayList<Button>(OPTIONS.length);

		for (int i=0;i<OPTIONS.length;i++) {
			buttons.add(new Button(OPTIONS[i], leftAlign, titleY + 20 + (int)((i+1)*1.5*Utility.LABEL_FONT.getSize()), Utility.LABEL_FONT));
		}

		drawing = new MainMenuDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * Removes the drawing and removes listeners
	  */
	public void halt() {
		CovidCashier.frame.remove(drawing);
		for (Button btn : buttons) btn.deactivate();
	}

	/**
	  * GUI for the instructions
	  */
	public class MainMenuDrawing extends MenuDrawing {
		/**
		 * Temporary order number
		 */
		int orderNumber = (int)(Math.random()*1000000);

		/**
		 * Timer
		 */
		Timer timer;

		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public MainMenuDrawing() {
			super();

			for (Button btn : buttons) btn.activate();
		}

		/**
		  * GUI display of the main menu
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			// System.out.println("main menu");
			g.setFont(Utility.TITLE_FONT_SMALL);
			g.setColor(Color.black);
			centerAlignStr(g, "COVID Cashier", 545, titleY);

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

			for (Button btn : buttons) {
				btn.draw(g);
				if (btn.isClicked()) {
					// do something
					halt();
					switch (btn.getName().toUpperCase()) {
						case "INSTRUCTIONS": 
							new Instructions();
							break;
						case "PLAY": 
							break;
						case "QUIT": 
							new Quit();
							break;
					}
					// System.out.println("here "+btn.getName());
				} 
			}
			refreshScreen();
		}

		/**
		  * Returns the y-coordinate of the title
		  * @return the MainMenuDrawing object's titleY attribute
		  */
		@Override
		public int getTitleY() {
			return titleY;
		}

		public void refreshScreen() {
			timer = new Timer(0, new ActionListener() {
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
