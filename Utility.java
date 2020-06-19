/**
  * Stores stylistic data and methods used throughout the program
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
import java.util.*;
import javax.imageio.*;

public final class Utility {
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
	public static final Font TITLE_FONT = loadFont("8-BIT_WONDER.TTF",42);

	/**
	  * Smaller font for titles
	  */
	public static final Font TITLE_FONT_SMALL = loadFont("8-BIT_WONDER.TTF",32);

	/**
	  * Font for labels, headings, and the like
	  */
	public static final Font LABEL_FONT = loadFont("DTM-Sans.otf",35);

	/**
	  * Font for descriptions, captions, and the like
	  */
	public static final Font TEXT_FONT = loadFont("Inconsolata-Medium.ttf",25);

	/**
	  * Font for small captions
	  */
	public static final Font TEXT_FONT_SMALL = loadFont("Inconsolata-Medium.ttf",15);

	/**
	  * Primary red shade of the graphics
	  */
	public static final Color RED = new Color(214, 0, 0);

	/**
	  * Primary cyan shade of the graphics
	  */
	public static final Color CYAN = new Color(233, 255, 251);

	/**
	  * The name of the resources folder
	  */
	public static final String RES_NAME = "/src";

	/**
	  * Caches loaded images
	  */
	public static final Map<String, Image> imageLibrary = new HashMap<String, Image>();

	/**
	  * Loads a final static Font object
	  * @param name 	the name of the file, including the extension
	  * @param size 	the desired font size
	  * @return a Font object of the given properties, or null if an Exception was thrown
	  */
	private static final Font loadFont(String name, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, Utility.class.getResourceAsStream(RES_NAME+"/fonts/"+name)).deriveFont(size);
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
		String key = name+"-"+width+"-"+height;
		if (imageLibrary.containsKey(key)) return imageLibrary.get(key);

		try {
			Image ret = ImageIO.read(Utility.class.getResourceAsStream(RES_NAME+"/img/"+name)).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			imageLibrary.put(key, ret);
			return ret;
		} catch (Exception e) {}
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
	  * Changes and updates the frame to the restauarant (which is assumed to be in pastDrawing)
	  */
	public static void backToRestaurant() {
		try {
			CovidCashier.getPastRestaurant().activate();
			changeDrawing(CovidCashier.getPastRestaurant().getDrawing());
			// CovidCashier.getPastRestaurant().bgm.resume();
		} catch (NullPointerException e) {}
	}

	/**
	  * Changes and updates the frame
	  * @param img 		the Image to "cast"
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
	  * Creates a BufferedReader from a given file
	  * @param name 	the name of the file, including the extension
	  * @return a BufferedReader object of the given file
	  */
	public static BufferedReader getBufferedReader(String name) {
		return new BufferedReader(new InputStreamReader(Utility.class.getResourceAsStream(RES_NAME+"/text/" + name)));
	}

	/**
	  * Gets the width of a String based on a given Font
	  * @param f 	the font to use for the text
	  * @param text the String to get the width of
	  * @return the width of the String, in pixels
	  */
	public static int getStringWidth(String text, Font f) {
		return (new JPanel()).getFontMetrics(f).charsWidth(text.toCharArray(),0,text.length());
	}

	/**
	  * Gets the width of a String based on a given Graphics object
	  * @param g 	the Graphics object whose current font is to be used
	  * @param text the String to get the width of
	  * @return the width of the String, in pixels
	  */
	public static int getStringWidth(String text, Graphics g) {
		return g.getFontMetrics().charsWidth(text.toCharArray(),0,text.length());
	}
}
