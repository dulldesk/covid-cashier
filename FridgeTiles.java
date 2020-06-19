/**
  * The Fridge minigame
  *
  * Last edit: 6/5/2020
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

public class FridgeTiles extends Minigame {
    /**
	  * Contains the minigame graphics / GUI
	  */
    private MinigameDrawing drawing;

    /**
	  * Contains the gender of the player.
	  */
    private char gender;

    /**
	  * Contains the type of protective equipment the player was wearing in the restaurant.
	  */
    private String equipment;

    /**
	  * Contains the "health" of the player in the minigame.
	  */
    private int health;

    /**
	  * Initializes and displays the drawing to the frame
	  */
    public FridgeTiles(char gender, String equipment) {
        this.gender = gender;
        this.equipment = equipment;
        health = 100;
        infoCard = new Dialogue("Fridge Tiles! It's important to only touch what you need in the fridge, to avoid spreading germs. Avoid all the food by using the left and right arrow keys. Good luck!", "Coworker");
        drawing = new FridgeTilesDrawing();
        Utility.changeDrawing(drawing);
        bgm = new BGM("fridgetiles");
        bgm.play();
    }

    /**
      * Overloaded constructor
      */
    public FridgeTiles(Character user) {
        this(user.getGender(), user.getPPE());
    }

    /**
	  * GUI for the minigame
	  */
    public class FridgeTilesDrawing extends MinigameDrawing {
        /**
		  * Player
		  */
        private Player player;

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
        private boolean refresh;

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
		public FridgeTilesDrawing() {
            super();
            player = new Player("player", gender);
            player.setDirection('N');
            player.setClothing('W');
            player.setEquipment(equipment);
            player.setCoordinates(287, 370);
            obstacles = new ArrayList<Obstacle>();
            spacing = 0;
            rand = 100;
            refresh = false;
            obstacleCount = 0;
            hit = 0;
            infoCard.activate();
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
            g.drawImage(Utility.loadImage("FridgeTiles_BG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            if(!end) {
                if(spacing > 20+rand && obstacleCount < 30) {
                    String[] names = {"Lettuce", "Onion", "Tomato", "Cheese", "Pickle", "Bacon", "Patty", "Ketchup", "Mustard"};
                    obstacles.add(new Obstacle(names[(int)(Math.random()*names.length)], 290+(int)(Math.random()*3)*80, -60, 480, 480));
                    spacing = 0;
                    rand = (int)(Math.random()*20);
                    obstacleCount++;
                }
                spacing++;
                for(int i = obstacles.size()-1; i >= 0; i--) {
                    Obstacle curr = obstacles.get(i);
                    curr.y_coord += 15;
                    curr.draw(g);
                    if(curr.isColliding(player)) {
                        if(!curr.collided) {
                            health -= 10;
                            hit++;
                        }
                        curr.collided = true;
                    }
                    if(!curr.withinFrame()) {
                        if(!curr.collided)
                            score += 10;
                        obstacles.remove(i);
                    }
                }
                if(refresh)
                    player.stepNo++;
                player.draw(g);
                if(spacing < rand && start) {
                    infoCard.draw(g);
                    if(infoCard.canProceed) {
                        spacing = rand-1;
                    }
                    player.fridgeTilesMovement.deactivate();
                } else {
                    start = false;
                    player.fridgeTilesMovement.activate();
                    infoCard.deactivate();
                }
                if(obstacles.size() == 0 && obstacleCount == 30 || health == 0) {
                    end = true;
                    hit = 0;
                    player.fridgeTilesMovement.deactivate();
                    if(health > 0)
                        infoCard = new Dialogue("Congratulations on completing Fridge Tiles! You finished with "+health+"% of your health, and a score of "+score+". Now get back to work!", "Coworker");
                    else
                        infoCard = new Dialogue("You didn't complete Fridge Tiles. Now get back to work!", "Coworker");
                    infoCard.activate();
                }
                refresh = !refresh;
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
                refreshScreen();
            } else {
                infoCard.draw(g);
                if(infoCard.canProceed) {
                    bgm.stop();
                    Utility.backToRestaurant();
                    return;
                }
            }
        }

        public void refreshScreen() {
			timer = new Timer(26, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();
				}
			});
			timer.setRepeats(false);
			timer.start();
        }

        public boolean isColliding(Player p, Obstacle o) {
            return p.y_coord < o.y_coord+o.height && p.y_coord+p.height > o.y_coord && p.x_coord < o.x_coord+o.width && p.x_coord+p.width-20 > o.x_coord;
        }
    }
}
