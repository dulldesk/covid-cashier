/**
  * The instructions screen
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Instructions extends Menu {
	/**
	  * Contains the instructions screen graphics / GUI
	  */
	private MenuDrawing drawing;

	/**
	  * Contains the actual text of the instructions
	  */
	private String info;

	/**
	  * Back (to main menu) button
	  */
	private Button back;

	/**
	  * Initializes and displays the drawing to the frame
	  */
	public Instructions() {
		info = "";
		try {
			BufferedReader br = Utility.getBufferedReader("instructions.md");
			
			// skip heading
			br.readLine();

			for (String nxt = br.readLine(); nxt != null; nxt = br.readLine()) info += nxt+'\n';
		} catch (Exception e) {System.out.println(e);}


		back = new ReturnButton(100,Utility.FRAME_HEIGHT-100);

		drawing = new InstructionsDrawing();
		Utility.changeDrawing(drawing);
	}

	/**
	  * Removes the drawing
	  */ 
	public void halt() {
		CovidCashier.frame.remove(drawing);
		back.deactivate();
	}

	/**
	  * GUI for the instructions
	  */
	public class InstructionsDrawing extends MenuDrawing {
		/**
		  * The y-coordinate of the title
		  */
		protected int titleY = 90;

		/**
		  * x-coordinate for left alignment of text
		  */
		protected int leftAlign = 90;

		/**
		  * Width of the information box
		  */
		private int nodeWidth;


		// private boolean firstTime;

		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public InstructionsDrawing() {
			super();

			nodeWidth =  Utility.FRAME_WIDTH-270;
			back.activate();
			// firstTime = true;
		}

		/**
		  * GUI display of the instructions screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
			// if (firstTime) {
			// 	// transition
			// 	firstTime = false;
			// }

			g.drawImage(Utility.loadImage("Register_Screen.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);

			g.setColor(Color.black);
			g.setFont(Utility.TITLE_FONT);
			g.drawString("INSTRUCTIONS",leftAlign,titleY);

			g.setFont(Utility.TEXT_FONT);
			drawInfo(g);

			back.draw(g);
			if (back.isClicked()) {
				halt();
				new MainMenu();
			}
		}


		/**
		  * Draws the instructions onto the screen
		  * @param g 	the Graphics object to draw on
		  */
		private void drawInfo(Graphics g) {
			String line = "";
			int row=1;
			String [] words = info.split(" ");
			for (String word : words) {
				if (Utility.getStringWidth(line,g) > nodeWidth) {
					g.drawString(line,leftAlign,titleY+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
					line = "";
					row++;
				} 
				line += word+" ";

				if (word.indexOf("\n") == word.length()-1) {
					g.drawString(line.trim(),leftAlign,titleY+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
					line = "";
					row++; 
				}
			}
			g.drawString(line,leftAlign,titleY+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
		}

		/**
		  * Returns the y-coordinate of the title
		  * @return the MainMenuDrawing object's titleY attribute
		  */
		@Override
		public int getTitleY() {
			return titleY;
		}
	}
}
