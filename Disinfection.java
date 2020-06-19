/**
  * The Disinfection minigame
  * 
  * Last edit: 6/17/2020
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

public class Disinfection extends Minigame {
    /**
	  * Contains the "health" of the player in the minigame.
	  */
    private int health;

    /**
	  * Initializes and displays the drawing to the frame
	  */
    public Disinfection() {
        health = 100;
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
         * ---
         */
        private int hit;

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
            hit = 0;
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(0, 0, Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
            Graphics2D g2d = (Graphics2D)g;
            if(hit == 1) {
                g2d.rotate(0.01, Utility.FRAME_WIDTH/2, Utility.FRAME_HEIGHT/2);
                hit++;
            } else if(hit == 2) {
                g2d.rotate(-0.01, Utility.FRAME_WIDTH/2, Utility.FRAME_HEIGHT/2);
                hit = 0;
            }
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
                        health -= 10;
                        hit++;
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
                    shooter.deactivate();
                } else {
                    start = false;
                    shooter.activate();
                }
                if(obstacles.size() == 0 && obstacleCount == 15 || health == 0) {
                    end = true;
                    hit = 0;
                    shooter.deactivate();
                    if(health > 0)
                        infoCard = new Dialogue("Congratulations on completing Disinfection! You finished with "+health+"% of your health, and a score of "+score+". Now get back to work!", "Coworker_MG");
                    else
                        infoCard = new Dialogue("You didn't complete Disinfection. Now get back to work!", "Coworker_MG");
                }
                g.drawImage(Utility.loadImage("HealthBar.png", 400, 50), Utility.FRAME_WIDTH/2-200, 25, null);
                g.setColor(new Color(214, 0, 0));
                g.fillRect(Utility.FRAME_WIDTH/2-190, 35, 38*health/10, 30);
                g.setColor(Color.black);
                g.setFont(Utility.TEXT_FONT);
                g.drawString("Score: "+String.format("%03d", score), Utility.FRAME_WIDTH/2-Utility.getStringWidth("Score: 000", Utility.TEXT_FONT)/2, 100);
                if(hit > 0) {
                    g.setColor(new Color(214, 0, 0, 50));
                    g.fillRect(0, 0, Utility.FRAME_WIDTH, Utility.FRAME_HEIGHT);
                }
            } else {
                infoCard.draw(g);
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
     * ---
     */
    private boolean canClick;

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
        canClick = false;
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
        if(canClick) {
            int x = (int)(20*Math.sin(Math.toRadians(angle)));
            int y = (int)(20*Math.cos(Math.toRadians(angle)));
            projectiles.add(new Projectile(x_coord-width/2+90/4-30/8, y_coord-height+36/4-30/8, 40, 40, x, y));
        }
    }
    public void mouseReleased(MouseEvent e) {}

    public void activate() {
        canClick = true;
    }
    public void deactivate() {
        canClick = false;
    }
}