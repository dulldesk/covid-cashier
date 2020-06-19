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
	  * No. of lines that can be displayed at once on the Register_Screen
	  */
	private static final int MAX_LINES = 7;

	private java.util.List<String[]> slides;

	private int pageNo;

	private KeyBindings arrowKeys;
	
	/**
	  * Width of the information box
	  */
	private static final int nodeWidth =  Utility.FRAME_WIDTH-270;

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

			br.close();
		} catch (Exception e) {System.out.println(e);}

		slides = loadSlides();
		pageNo = 0;

		back = new ReturnButton(100,Utility.FRAME_HEIGHT-100);
		arrowKeys = new KeyBindings();

		drawing = new InstructionsDrawing();
		Utility.changeDrawing(drawing);
		bgm = new BGM("menus");
		bgm.play();
	}

	/**
	  * Activates listeners
	  */
	public void activate() {
		back.activate();
		arrowKeys.activate();
	}

	/**
	  * Removes the drawing
	  */ 
	public void halt() {
		CovidCashier.frame.remove(drawing);
		back.deactivate();
		arrowKeys.deactivate();
	}

	/**
	  * Generates a List where each element is the lines to be displayed on the instructions screen at once
	  * @return a List with the instructions split up per screen 
	  */
	private java.util.List<String[]> loadSlides() {
		java.util.List<String[]> slides = new ArrayList<String[]>();
		String [] lines = new String[MAX_LINES];

		String line = "";
		int row=0;
		String [] words = info.split(" ");
		for (String word : words) {
			if (Utility.getStringWidth(line,Utility.TEXT_FONT) > nodeWidth) {
				lines[row++] = line;
				line = "";
				if (row == MAX_LINES) {
					row = 0;
					slides.add(lines);
					lines = new String[MAX_LINES];
				}
			} 
			line += word+" ";

			if (word.indexOf("\n") == word.length()-1) {
				lines[row++] = line;
				line = "";
				if (row == MAX_LINES) {
					row = 0;
					slides.add(lines);
					lines = new String[MAX_LINES];
				}
			}
		}
		if (row != 0 || !line.trim().equals("")) {
			lines[row] = line;
			slides.add(lines);
		}

		return slides;
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

		// private boolean firstTime;c

		/**
		  * Object constructor. Uses the superclass's constructor
		  */
		public InstructionsDrawing() {
			super();

			activate();
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

			String note = "Use your arrow keys to flip between the";
			g.setFont(Utility.TEXT_FONT_SMALL);
			g.drawString(note, Utility.FRAME_WIDTH-50-Utility.getStringWidth(note, g), 435);
			note = "pages of the instructions";
			g.drawString(note, Utility.FRAME_WIDTH-50-Utility.getStringWidth(note, g), 455);

			back.draw(g);
			if (back.isClicked()) {
				halt();
				bgm.stop();
				new MainMenu();
			}
		}

		/**
		  * Draws the instructions onto the screen
		  * @param g 	the Graphics object to draw on
		  */
		private void drawInfo(Graphics g) {
			String [] lines = slides.get(pageNo);

			int row = 1;
			for (String line : lines) {
				if (line == null) return;
				g.drawString(line,leftAlign,titleY+20+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
				row++;
			}
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

	private class KeyBindings extends ScreenMovement {
		public KeyBindings() {
			super("instructions");
		}

		@Override
		protected void loadKeyBindings() {
			String [] keys = {"left", "right"};
			int [] strokes = {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};

			for (int i=0; i<keys.length; i++) {
				final int index = i;
				movementMap.put(keys[index], new Movement(keys[index], KeyStroke.getKeyStroke(strokes[index], 0), new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						if (keys[index].equalsIgnoreCase("right")) {
							pageNo++;
							if (pageNo >= slides.size()) pageNo = 0;
						} else {
							pageNo--;
							if (pageNo < 0) pageNo = slides.size()-1;
						}
						CovidCashier.frame.repaint();
					}
				}));
			}

		}
	}
}
