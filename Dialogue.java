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

public class Dialogue extends GraphicComponent {
	// private final String [] text_lines;

	// private final int text_length;

	// private int nextIndex;

	private String text;

	public static final Image BOX; 

	private Image face;

	/**
	  * The side length of a face
	  */
	private static final int FACE_SIZE = 75;

	private static final int BOX_WIDTH = 448;

	private static final int BOX_HEIGHT = 120;

	private final int PADDING;

	protected boolean isEntered;

	private EntryBinding enterKey;

	// private Button next;

	static {
		BOX = Utility.loadImage("Dialogue_Box.png",BOX_WIDTH,BOX_HEIGHT);
	}

	/**
	  * Constructs a Dialogue object
	  * @param text		x-coordinate (top-left corner)
	  * @param charType	the type of character (used for the face)
	  */
	public Dialogue(String text, String charType) {
		this(text,"=",true);

		int lastUnderscore = charType.indexOf("_");
		face = Utility.loadImage((lastUnderscore == -1 ? charType : charType.substring(0, lastUnderscore))+"_Face.png", FACE_SIZE, FACE_SIZE);
	}

	/**
	  * Constructs a Dialogue object
	  * @param text		x-coordinate (top-left corner)
	  * @param prompt	the prompt to be used to go to the next box or exit the dialogue
	  */
	public Dialogue(String text, String prompt, boolean addFace) {
		// default values 
		// from super class
		width = BOX_WIDTH;
		height = BOX_HEIGHT;

		x_coord = (Utility.FRAME_WIDTH - BOX_WIDTH) / 2;
		y_coord = Utility.FRAME_HEIGHT - BOX_HEIGHT - 15;

		if (!addFace) face = null;
	

		this.text = text;
		// text_length = text.length();
		// text_lines = new String[BOX_WIDTH];

		PADDING = (height - FACE_SIZE)/2;

		isEntered = false;
		enterKey = new EntryBinding();

		// next = new Button(prompt,x_coord+width-20,y_coord+height-20,Utility.TEXT_FONT,Color.white,Color.grey);
	}

	public void activate() {
		// next.activate();
		isEntered = false;
		enterKey.activate();
	}

	public void deactivate() {
		// next.deactivate();
		enterKey.deactivate();
		isEntered = false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(BOX,x_coord,y_coord,null);
		if (face != null) g.drawImage(face,x_coord + PADDING,y_coord + PADDING,null);

		g.setColor(Color.white);
		g.setFont(Utility.TEXT_FONT);
		drawText(g);

		// next.draw();
		
		// if (nextIndex != textArray.length) nextIndex++;
	}

	private void drawText(Graphics g) {
		final int leftAlign =x_coord + PADDING+FACE_SIZE+20;
		String line = "";
		int row=1;
		String [] words = text.split(" ");
		for (String word : words) {
			if (Utility.getStringWidth(line,g) > width) {
				g.drawString(line,leftAlign,y_coord+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
				line = "";
				row++;
			} 
			line += word+" ";

			if (word.indexOf("\n") == word.length()-1) {
				g.drawString(line.trim(),leftAlign,y_coord+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
				line = "";
				row++; 
			}
		}
		g.drawString(line,x_coord + PADDING+FACE_SIZE+10,y_coord+(int)(row*1.5*Utility.TEXT_FONT.getSize()));
	}

	public boolean isEntered() {
		return isEntered;
	}

	@Override
	protected boolean withinCoordinates() {return false;}

	private class EntryBinding extends ScreenMovement {
		public EntryBinding() {
			super("dialogue");
		}

		protected void loadKeyBindings() {
			movementMap.put("continue",new Movement("continue", KeyEvent.VK_ENTER, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					isEntered = true;
					CovidCashier.frame.repaint();
				}
			}));
		}
	}
}
