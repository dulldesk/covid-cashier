/**
  * The Obstacle class
  * 
  * Last edit: 6/17/2020
  * @author 	Eric
  * @version 	1.2
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
     * The obstacle's angle of rotation
     */
    protected double angle;

    /**
     * The collision boolean
     */
    protected boolean collided;
    
    /**
	   * Constructs a Obstacle object and loads the appropriate sprite
	   * @param n 	    the Obstacle's name
	   * @param x     	the x coordinate
	   * @param y    	  the y coordinate
     * @param w       the width
     * @param h       the height
	   */
    public Obstacle(String n, int x, int y, int w, int h, double a) {
        name = n;
        x_coord = x;
        y_coord = y;
        width = w/8;
        height = h/8;
        angle = a;
        obstacle = Utility.loadImage(name+".png", width, height);
    }
    /**
	   * Constructs a Obstacle object and loads the appropriate sprite
	   * @param n 	    the Obstacle's name
	   * @param x     	the x coordinate
	   * @param y    	  the y coordinate
     * @param w       the width
     * @param h       the height
	   */
    public Obstacle(String n, int x, int y, int w, int h) {
        this(n, x, y, w, h, 0);
    }
    
    /**
	   * Draws the obstacle to the window
	   * @param g 	The Graphics object to draw on
	   */
	  public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(x_coord, y_coord);
        g2d.rotate(Math.toRadians(angle), width/2, height/2);
        g.drawImage(obstacle, 0, 0, null);
        g2d.rotate(Math.toRadians(-angle), width/2, height/2);
        g2d.translate(-x_coord, -y_coord);
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