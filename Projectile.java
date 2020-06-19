/**
  * The Disinfection minigame
  * 
  * Last edit: 6/18/2020
  * @author 	Eric
  * @version 	1.1
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;

public class Projectile {
    /**
     * The projectile's x-coordinate (top left corner)
     */
    protected int x_coord;

    /**
     * The projectile's y-coordinate (top left corner)
     */
    protected int y_coord;

    /**
     * The projectile's width
     */
    protected int width;

    /**
     * The projectile's height
     */
    protected int height;

    /**
     * The projectile's x-direction
     */
    protected int x_dir;

    /**
     * The projectile's y-direction
     */
    protected int y_dir;

    /**
     * The projectile image
     */
    protected Image projectile;

    /**
     * Constructs a Shooter object and loads the appropriate sprite
     * @param x     	the x coordinate
     * @param y    	    the y coordinate
     * @param w         the width
     * @param h         the height
     * @param xd        the x direction
     * @param yd        the y direction
     */
    public Projectile(int x, int y, int w, int h, int xd, int yd) {
        x_coord = x;
        y_coord = y;
        width = w/4;
        height = h/4;
        x_dir = xd;
        y_dir = yd;
        projectile = Utility.loadImage("Projectile.png", width, height);
    }

    /**
     * Draws the projectile to the window
     * @param g     The Graphics object to draw on
     */
    public void draw(Graphics g) {
        g.drawImage(projectile, x_coord, y_coord, null);
    }

    public boolean withinFrame() {
        return x_coord < Utility.FRAME_WIDTH && x_coord+width > 0 && y_coord < Utility.FRAME_HEIGHT && y_coord+height > 0;
    }
}