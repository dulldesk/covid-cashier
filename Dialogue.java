/**
  * A Dialogue object to host dialogue
  * 
  * Last edit: 6/12/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Dialogue extends GraphicComponent {
	/**
	  * The full text to be displaed
	  */
	private final String fullText;

	/**
	  * The image of the Dialogue box
	  */
	public static final Image BOX; 

	/**
	  * The face icon to be displayed
	  */
	private Image face;

	/**
	  * The side length of a face
	  */
	private static final int FACE_SIZE = 75;

	/**
	  * The width of the box
	  */
	private static final int BOX_WIDTH = Utility.FRAME_WIDTH - 240;

	/**
	  * The height of the box
	  */
	private static final int BOX_HEIGHT = 150;

	/**
	  * The text padding of the box
	  */
	private static final int PADDING;

	/**
	  * Whether the enter key has been pressed
	  */
	protected boolean isEntered;

	/**
	  * Listens for the enter key
	  */
	private EntryBinding enterKey;

	/**
	  * Stores the lines to appear on each dialogue box
	  * <p> 
	  * The dialogue to be shown may require more than one box in order to show the full content.
	  * Each element in the queue represents one "box" of dialogue
	  * To move onto the next box, the user presses the enter key
	  * Each String of the String [] element represents one row of the box's text
	  * Queue elements are in order of display
	  */
	protected Queue<String[]> textQueue;

	/**
	  * Whether all of the dialogue "screens" have been displayed
	  */
	protected boolean canProceed;

	static {
		BOX = Utility.loadImage("Dialogue_Box.png",BOX_WIDTH,BOX_HEIGHT);
		PADDING = (BOX_HEIGHT - FACE_SIZE)/2-20;
	}

	/**
	  * Constructs a Dialogue object
	  * @param text		the entire text to be displayed by the dialogue
	  * @param charType	the type of character (used for the face)
	  */
	public Dialogue(String text, String charType) {
		this(text,"=",true);

		int lastUnderscore = charType.indexOf("_");
		face = Utility.loadImage((lastUnderscore == -1 ? charType : charType.substring(0, lastUnderscore))+"_Face.png", FACE_SIZE, FACE_SIZE);
	}

	/**
	  * Constructs a Dialogue object
	  * @param text		the entire text to be displayed by the dialogue
	  * @param prompt	the prompt to be used to go to the next box or exit the dialogue
	  * @param addFace 	whether to add a face onto the dialogue or not. Primary purpose is to distinguish between overloaded methods
	  */
	public Dialogue(String text, String prompt, boolean addFace) {
		this(text);
	}

	/**
	  * Constructs a Dialogue object
	  * @param text		the entire text to be displayed by the dialogue
	  */
	public Dialogue(String text) {
		// default values 
		// from super class
		width = BOX_WIDTH;
		height = BOX_HEIGHT;

		x_coord = (Utility.FRAME_WIDTH - BOX_WIDTH) / 2;
		y_coord = Utility.FRAME_HEIGHT - BOX_HEIGHT - 20;
		text_font = Utility.TEXT_FONT_SMALL;

		face = null;

		fullText = text;

		canProceed = false;
		isEntered = false;
		textQueue = loadTextQueue();

		enterKey = new EntryBinding();
	}

	/** 
	  * Activates listeners and the like for the object
	  */
	public void activate() {
		isEntered = false;
		canProceed = false;
		enterKey.activate();
	}

	/** 
	  * Deactivates listeners and the like for the object
	  */
	public void deactivate() {
		isEntered = false;
		canProceed = false;
		enterKey.deactivate();
	}

	public boolean isEntered() {
		return isEntered;
	}

	public boolean canProceed() {
		return canProceed;
	}

	@Override
	public void draw(Graphics g) {
		if (canProceed || textQueue.size() == 0) return;

		// System.out.println(textQueue.size() + " "+ textQueue.hashCode());

		g.drawImage(BOX,x_coord,y_coord,null);
		if (face != null) g.drawImage(face,x_coord + PADDING-5,y_coord + PADDING+15,null);

		g.setColor(Color.white);
		g.setFont(text_font);
		drawText(g);
	}

	/** 
	  * Draws the text for the current dialogue "page" onto the screen
	  * @param g the Graphics object to be drawn on
	  */
	private void drawText(Graphics g) {
		final int leftAlign = x_coord + PADDING + (face != null ? FACE_SIZE : 10);
		String [] lines = textQueue.peek();

		for (int row=1;row<=lines.length; row++) {
			if (lines[row-1]==null) return;
			g.drawString(lines[row-1].trim(),leftAlign,y_coord+PADDING+(int)(row*1.5*text_font.getSize()));
		}
	}

	/**
	  * Loads a queue holding a String array of the boxes to be shown (i.e. the textQueue)
	  * @return a queue storing an array of Strings representing each Dialogue "box"/screen to be displayed
	  */
	private Queue<String[]> loadTextQueue() {
		final int MAX_ROW = 5;
		// fullText
		Queue<String[]> queue = new LinkedList<String[]>();
		String [] lines = fullText.split("\n");

		for (String ln : lines) {
			String line = "";
			String [] words = ln.split(" ");
			int row=0;
			String [] screenText = new String[MAX_ROW];

			for (String word : words) {
				boolean secondCondition = word.indexOf("\n") == word.length()-1;

				if (Utility.getStringWidth(line + word + " ",text_font) >= width-PADDING*2-(face != null ? FACE_SIZE+60 : FACE_SIZE+30) || secondCondition) {
					if (secondCondition) {
						line += word+" ";
					}

					screenText[row++] = line.trim();
					line = "";

					if (!secondCondition) {
						line = word+" ";
					} else {
						row = MAX_ROW;
					}

					if (row >= MAX_ROW) {
						queue.add(screenText);
						screenText = new String[MAX_ROW];
						row = 0;
					}
				} else {
					line += word+" ";
				}
			}
			if (!line.trim().equals(""))  {
				screenText[row] = line.trim();
				line = "";
				row = 0;
				queue.add(screenText);
			}
		}

		return queue;
	}

	/**
	  * @return whether the object has nothing to display
	  */
	public boolean isEmpty() {
		return fullText.equals("");
	}

	@Override
	protected boolean withinCoordinates() {return false;}

	/**
	  * Key binding object to detect the enter key
	  */
	private class EntryBinding extends ScreenMovement {
		public EntryBinding() {
			super("dialogue");
		}

		@Override
		protected void loadKeyBindings() {
			movementMap.put("continue",new Movement("continue", KeyEvent.VK_ENTER, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					isEntered = true;

					// only pop from the queue if the size is greater than 0
					if (!canProceed) textQueue.poll();
					canProceed = textQueue.size() == 0;

					if (canProceed) System.out.println("can proceed");

					CovidCashier.frame.repaint();
				}
			}));
		}
	}
}
