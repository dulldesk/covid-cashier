/**
  * Stores stylistic and user selection data
  * 
  * Last edit: 5/25/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import javax.swing.JFrame;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public final class Style {
	/**
	  * The width of the frame, in pixels
	  */
	public static final int FRAME_WIDTH = 816;

	/**
	  * The height of the frame, in pixels
	  */
	public static final int FRAME_HEIGHT = 539;

	/**
	  * Font for titles
	  */
	public static final Font TITLE_FONT = loadFont("8-BIT_WONDER.ttf",42);

	/**
	  * Font for labels, headings, and the like
	  */
	public static final Font LABEL_FONT = loadFont("DTM-Sans.otf",42);

	/**
	  * Font for descriptions, captions, and the like
	  * [to change]
	  */
	public static final Font TEXT_FONT = new Font("Calibri",Font.PLAIN,35);

	/**
	  * Loads a final static Font object
	  * @param name 	the name of the file, including the extension
	  * @param size 	the desired font size
	  * @return a Font object of the given properties, or null if an Exception was thrown
	  */
	private static final Font loadFont(String name, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, Style.class.getResourceAsStream("src/fonts/"+name)).deriveFont(size);
		} catch (Exception e) {}
		return null;
	}

	/**
	  * Creates an Image object from a file
	  * @param name 	the name of the file, including the extension
	  * @param width 	the desired width of the image, in pixels
	  * @param height 	the desired height of the image, in pixels
	  * @return an Image object of the given specifications, or null if an IOException was thrown
	  */
	public static final Image loadImage(String name, int width, int height) {
		try {
			return ImageIO.read(Style.class.getResourceAsStream("src/img/"+name)).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {}
		return null;
	}
}
