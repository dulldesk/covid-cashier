/**
  * The Fridge minigame
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

public class FridgeTiles extends Minigame {
    /**
	  * Contains the gender of the player.
	  */
    private char gender;

    /**
	  * Contains the type of protective equipment the player was wearing in the restaurant.
	  */
    private String equipment;

    /**
	  * Initializes and displays the drawing to the frame
	  */
    public FridgeTiles(char gender, String equipment) {
        this.gender = gender;
        this.equipment = equipment;
        infoCard = new Dialogue("Fridge Tiles! It's important to only touch what you need in the fridge, to avoid spreading germs. Avoid all the food by using the left and right arrow keys. Good luck!", "Coworker_MG");
        drawing = new FridgeTilesDrawing();
        Utility.changeDrawing(drawing);
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
		  * Object constructor. Uses the superclass's constructor
		  */
		public FridgeTilesDrawing() {
            super();
            player = new Player("player", gender);
            player.setDirection('N');
            player.setClothing('W');
            player.setEquipment(equipment);
            player.setCoordinates(287, 370);
            player.fridgeTilesMovement.activate();
            obstacles = new ArrayList<Obstacle>();
            spacing = 0;
            rand = 100;
            refresh = false;
            obstacleCount = 0;
            infoCard.activate();
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
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
                        if(!curr.collided)
                            score -= 10;
                        curr.collided = true;
                    }
                    if(!curr.withinFrame()) {
                        obstacles.remove(i);
                    }
                }
                if(refresh)
                    player.stepNo++;
                player.draw(g);
                if(spacing < rand && start) {
                    infoCard.draw(g);
                } else {
                    start = false;
                    infoCard.deactivate();
                }  
                if(obstacles.size() == 0 && obstacleCount == 30)
                    end = true;
                refresh = !refresh;
                g.setColor(Color.black);
                g.setFont(Utility.LABEL_FONT);
                g.drawString("Score: "+String.format("%03.0f", score), Utility.FRAME_WIDTH/2-Utility.getStringWidth("Score: 000", Utility.LABEL_FONT)/2, 50);
            }
            //try{Thread.sleep(17);}catch(Exception e){}
            //repaint();
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