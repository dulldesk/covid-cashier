/**
  * The Cash Run minigame
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

public class CashRun extends Minigame {
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
    public CashRun(char gender, String equipment) {
        this.gender = gender;
        this.equipment = equipment;
        infoCard = new Dialogue("Cash Run! Germs can survive on paper money for a long time. Jump over all the paper bills with the space bar and collect credit cards by running through them. Good luck!", "Coworker_MG");
        drawing = new CashRunDrawing();
        Utility.changeDrawing(drawing);
    }

    public CashRun(Character user) {
        this(user.getGender(), user.getPPE());
    }

    /**
	  * GUI for the minigame
	  */
    public class CashRunDrawing extends MinigameDrawing {
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
		public CashRunDrawing() {
            super();
            player = new Player("player", gender);
            player.setDirection('E');
            player.setClothing('W');
            player.setEquipment(equipment);
            player.setCoordinates(40, 211);
            player.cashRunMovement.activate();
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
            g.drawImage(Utility.loadImage("CashRun_BG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            if(!end) {
                if(spacing > 20+rand && obstacleCount < 25) {
                    String name = (Math.random()<0.5?"Cash":"Card");
                    obstacles.add(new Obstacle(name, 800, 250, (name.equals("Cash")?288:320), 480));
                    spacing = 0;
                    rand = (int)(Math.random()*20);
                    obstacleCount++;
                }
                spacing++;
                for(int i = obstacles.size()-1; i >= 0; i--) {
                    Obstacle curr = obstacles.get(i);
                    curr.x_coord -= 15;
                    curr.draw(g);
                    if(curr.isColliding(player)) {
                        if(!curr.collided) {
                            if(curr.name.equals("Cash"))
                                score -= 10;
                            else
                                score += 10;
                        }
                        curr.collided = true;
                    }
                    if(!curr.withinFrame())
                        obstacles.remove(i);
                }
                if(!player.jumped) {
                    // if(!player.activated)
                    player.cashRunMovement.activate();
                    if(refresh)
                        player.stepNo++;
                } else {
                    player.cashRunMovement.deactivate();
                    player.stepNo = 1;
                    if(player.speed > -44) {
                        player.speed-=8;
                        //System.out.println(player.speed);
                    } else {
                        player.jumped = false;
                        player.speed = 0;
                    }
                }
                player.y_coord -= player.speed;
                player.draw(g);
                if(spacing < rand && start) {
                    infoCard.draw(g);
                } else {
                    start = false;
                    infoCard.deactivate();
                }
                if(obstacles.size() == 0 && obstacleCount == 25)
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