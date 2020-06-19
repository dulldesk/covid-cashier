/**
  * The Obstacle class
  * 
  * Last edit: 6/4/2020
  * @author 	Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;

public class Obstacle {
    /**
	  * The obstacle's x-coordinate (top left corner)
	  */
    protected int x_coord;

    /**
	  * The obstacle's y-coordinate (top left corner)
	  */
    protected int y_coord;

    /**
	  * The obstacle's width
	  */
    protected int width;

    /**
	  * The obstacle's height
	  */
    protected int height;

    /**
	  * The obstacle's name
	  */
    protected String name;

    /**
	  * The obstacle image
	  */
    protected Image obstacle;

    /**
	  * Constructs a Obstacle object and loads the appropriate sprite
	  * @param n 	    the Obstacle's name
	  * @param x     	the x coordinate
	  * @param y    	the y coordinate
	  */
    public Obstacle(String n, int x, int y, int w, int h) {
        name = n;
        x_coord = x;
        y_coord = y;
        width = (int)(w/8);
        height = (int)(h/8);
        obstacle = Utility.loadImage(name+".png", width, height);
    }

    /**
	  * Draws the character to the window
	  * @param g 	The Graphics object to draw on
	  */
    public void draw(Graphics g) {
        g.drawImage(obstacle, x_coord, y_coord, null);
    }
    public boolean isColliding(Player p) {
        return p.y_coord < y_coord+height && p.y_coord+p.height > y_coord && p.x_coord < x_coord+width && p.x_coord+p.width > x_coord;
    }
    public boolean isColliding(Projectile p) {
        return p.y_coord < y_coord+height && p.y_coord+p.height > y_coord && p.x_coord < x_coord+width && p.x_coord+p.width > x_coord;
    }
    public boolean withinFrame() {
        return x_coord < Utility.FRAME_WIDTH && x_coord+width > 0 && y_coord < Utility.FRAME_HEIGHT && y_coord+height > 0;
    }
}