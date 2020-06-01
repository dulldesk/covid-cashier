/**
  * Stores stylistic and user selection data
  * 
  * Last edit: 6/1/2020
  * @author 	Celeste, Eric
  * @version 	1.1
  * @since 		1.0
  */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public final class Style {
	/**
	  * The width of the frame, in pixels
	  */
	public static final int FRAME_WIDTH = 800;

	/**
	  * The height of the frame, in pixels
	  */
	public static final int FRAME_HEIGHT = 500;

	/**
	  * Font for titles
	  */
	public static final Font TITLE_FONT = loadFont("8-BIT_WONDER.ttf",42);

	/**
	  * Smaller font for titles
	  */
	public static final Font TITLE_FONT_SMALL = loadFont("8-BIT_WONDER.ttf",32);

	/**
	  * Font for labels, headings, and the like
	  */
	public static final Font LABEL_FONT = loadFont("DTM-Sans.otf",35);

	/**
	  * Font for descriptions, captions, and the like
	  * [to change]
	  */
	public static final Font TEXT_FONT = loadFont("Inconsolata-Medium.ttf",25);

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

	/**
	  * Changes and updates the frame
	  * @param drawing 		the component to be updated to
	  */
	public static void changeDrawing(JComponent drawing) {
		CovidCashier.frame.setContentPane(drawing);
		CovidCashier.frame.revalidate();
		CovidCashier.frame.repaint();
	}

	/**
	  * Changes and updates the frame
	  * @param drawing 		the component to be updated to
	  */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage)
        	return (BufferedImage) img;
    	BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bImage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
    	return bImage;
	}

	/**
	  * Returns the width of a String based on a given Graphics object
	  * @param g 	the Graphics object whose current font is to be used
	  * @param text the String to get the width of
	  * @return the width of the String, in pixels
	  */
	public static int getStringWidth(String text, Graphics g) {
		return g.getFontMetrics().charsWidth(text.toCharArray(),0,text.length());
	}
}
