/**
  * The Cash Run minigame
  * 
  * Last edit: 6/3/2020
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

public class CashRun extends Minigame {
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
    public CashRun(char gender, String equipment) {
        this.gender = gender;
        this.equipment = equipment;
        drawing = new CashRunDrawing();
        Utility.changeDrawing(drawing);
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
		  * Object constructor. Uses the superclass's constructor
		  */
		public CashRunDrawing() {
            super();
            player = new Player("player", gender);
            player.setDirection('E');
            player.setClothing('W');
            player.setEquipment(equipment);
            player.setCoordinates(40, 207);
            player.cashRunActivate();
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            g.drawImage(Utility.loadImage("CashRun_BG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            if(!player.jumped) {
                player.cashRunActivate();
                player.stepNo++;
            } else {
                player.cashRunDeactivate();
                player.stepNo = 1;
                if(player.dist > -22) {
                    player.dist-=2;
                    System.out.println(player.dist);
                }
                else {
                    player.jumped = false;
                    player.dist = 0;
                }
            }
            player.y_coord -= player.dist;
            player.draw(g);
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
			//Aprox. 60 FPS
			timer.setDelay(17);
			timer.start();
		}
    }
}