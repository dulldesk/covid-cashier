/**
  * The Disinfection minigame
  * 
  * Last edit: 6/17/2020
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

public class Disinfection extends Minigame {
    /**
	  * Initializes and displays the drawing to the frame
	  */
    public Disinfection() {
        infoCard = new Dialogue("Disinfection! It is important to regularly disinfect surfaces. Shoot the virus down with disinfectant by clicking your mouse. Good luck!", "Coworker_MG");
        drawing = new DisinfectionDrawing();
        Utility.changeDrawing(drawing);
    }

    /**
	  * GUI for the minigame
	  */
    public class DisinfectionDrawing extends MinigameDrawing {
        /**
         * Shooter
         */
        private Shooter shooter;

        /**
		 * Timer
		 */
        private Timer timer;

        /**
         * List of Obstacles
         */
        private ArrayList<Obstacle> obstacles;

        /**
         * ---
         */
        private int rand;

        /**
         * ---
         */
        private int spacing;

        /**
         * ---
         */
        private int obstacleCount;

        /**
		  * Object constructor. Uses the superclass's constructor
		  */
		public DisinfectionDrawing() {
            super();
            shooter = new Shooter(Utility.FRAME_WIDTH/2, Utility.FRAME_HEIGHT, 180, 360);
            obstacles = new ArrayList<Obstacle>();
            spacing = 0;
            rand = 100;
            obstacleCount = 0;
            infoCard.activate();
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            g.drawImage(Utility.loadImage("Disinfection_BG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            if(!end) {
                if(spacing > 20+rand && obstacleCount < 15) {
                    String name = "Virus";
                    obstacles.add(new Obstacle(name, 100+(int)(Math.random()*600), -50, 400, 400, (int)(Math.random()*3)*90));
                    spacing = 0;
                    rand = (int)(Math.random()*20);
                    obstacleCount++;
                }
                spacing++;
                for(int i = obstacles.size()-1; i >= 0; i--) {
                    Obstacle curr = obstacles.get(i);
                    ArrayList<Integer> collideIndex = new ArrayList<>();
                    curr.y_coord += 5;
                    curr.draw(g);
                    for(int j = shooter.projectiles.size()-1; j >= 0; j--) {
                        Projectile p = shooter.projectiles.get(j);
                        if(curr.isColliding(p))
                            collideIndex.add(j);
                    }
                    if(!curr.withinFrame()) {
                        obstacles.remove(i);
                        score -= 10;
                    } else if(!collideIndex.isEmpty()) {
                        obstacles.remove(i);
                        for(Integer index : collideIndex)
                            shooter.projectiles.remove(index.intValue());
                        score += 10;
                    }
                }
                shooter.draw(g);
                if(spacing < rand && start) {
                    infoCard.draw(g);
                } else {
                    start = false;
                    infoCard.deactivate();
                }
                if(obstacles.size() == 0 && obstacleCount == 15)
                    end = true;
                g.setColor(Color.black);
                g.setFont(Utility.LABEL_FONT);
                g.drawString("Score: "+String.format("%03.0f", score), Utility.FRAME_WIDTH/2-Utility.getStringWidth("Score: 000", Utility.LABEL_FONT)/2, 50);
            }
            refreshScreen();
        }

        public void refreshScreen() {
			timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();
				}
			});
			timer.setRepeats(false);
			timer.start();
        }
    }
}

class Shooter implements MouseListener, MouseMotionListener {
    /**
     * The shooter's x-coordinate (top center)
     */
    protected int x_coord;

    /**
     * The shooter's y-coordinate (top center)
     */
    protected int y_coord;

    /**
     * The shooter's width
     */
    protected int width;

    /**
     * The shooter's height
     */
    protected int height;

    /**
     * The shooter's angle of rotation
     */
    protected double angle;

    /**
     * The shooter image
     */
    protected Image shooter;

    /**
     * List of Obstacles
     */
    protected ArrayList<Projectile> projectiles;

    /**
     * Constructs a Shooter object and loads the appropriate sprite
     * @param n         the Shooter's name
     * @param x     	the x coordinate
     * @param y    	    the y coordinate
     * @param w         the width
     * @param h         the height
     * @param a         the angle
     */
    public Shooter(int x, int y, int w, int h, double a) {
        x_coord = x;
        y_coord = y;
        width = w/4;
        height = h/4;
        angle = a;
        shooter = Utility.loadImage("SprayBottle.png", width, height);
        projectiles = new ArrayList<Projectile>();
        CovidCashier.frame.addMouseListener(this);
        CovidCashier.frame.addMouseMotionListener(this);
    }

    /**
     * Constructs a Shooter object and loads the appropriate sprite
     * @param n         the Shooter's name
     * @param x     	the x coordinate
     * @param y    	    the y coordinate
     * @param w         the width
     * @param h         the height
     * @param a         the angle
     */
    public Shooter(int x, int y, int w, int h) {
        this(x, y, w, h, 0);
    }

    /**
     * Draws the shooter to the window
     * @param g     The Graphics object to draw on
     */
    public void draw(Graphics g) {
        for(int i = projectiles.size()-1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.x_coord -= p.x_dir;
            p.y_coord -= p.y_dir;
            p.draw(g);
            if(!p.withinFrame())
                projectiles.remove(i);
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(x_coord-width/2, y_coord-height);
        g2d.rotate(Math.toRadians(90-angle), 90/4, 36/4);
        g.drawImage(shooter, 0, 0, null);
        g2d.rotate(Math.toRadians(-90+angle), 90/4, 36/4);
        g2d.translate(-x_coord+width/2, -y_coord+height);
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        double x = x_coord-e.getXOnScreen();
        double y = y_coord-height+36/4-e.getYOnScreen();
        angle = Math.toDegrees(Math.atan(x/y));
        if(y < 0) angle -= 180;
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        int x = (int)(20*Math.sin(Math.toRadians(angle)));
        int y = (int)(20*Math.cos(Math.toRadians(angle)));
        projectiles.add(new Projectile(x_coord-width/2+90/4-30/8, y_coord-height+36/4-30/8, 40, 40, x, y));
    }
    public void mouseReleased(MouseEvent e) {}
}

class Projectile {
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