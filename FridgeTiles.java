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
	  * Initializes and displays the drawing to the frame
	  */
    public FridgeTiles(char gender, String equipment) {
        this.gender = gender;
        this.equipment = equipment;
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
            rand = (int)(Math.random()*20);
            refresh = false;
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            g.drawImage(Utility.loadImage("FridgeTiles_BG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            if(spacing > 15+rand) {
                String name = "Temp";
                obstacles.add(new Obstacle(name, 290+(int)(Math.random()*3)*80, -100, 480, 480));
                spacing = 0;
                rand = (int)(Math.random()*15);
            }
            spacing++;
            for(int i = obstacles.size()-1; i >= 0; i--) {
                Obstacle curr = obstacles.get(i);
                curr.y_coord += 15;
                curr.draw(g);
                if(isColliding(player, curr)) {
                    score -= 10;
                }
                if(curr.y_coord > 500)
                    obstacles.remove(i);
            }
            if(refresh)
                player.stepNo++;
            player.draw(g);
            refresh = !refresh;
            g.drawString("Score: "+score, 10, 15);
            g.setColor(Color.blue);
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
        
        public boolean isColliding(Player p, Obstacle o) {
            return p.y_coord < o.y_coord+o.height && p.y_coord+p.height > o.y_coord && p.x_coord < o.x_coord+o.width && p.x_coord+p.width-20 > o.x_coord;
        }
    }
}