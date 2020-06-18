/**
  * The Disinfection minigame
  * 
  * Last edit: 6/18/2020
  * @author 	Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;
import java.io.*;

public class MemoryGame extends Minigame {
    /**
	  * Initializes and displays the drawing to the frame
	  */
    public MemoryGame() {
        infoCard = new Dialogue("Memory! As a cashier, you must be able to remember customers' orders. Click cards to flip over and match pairs. Good luck!", "Coworker_MG");
        drawing = new MemoryGameDrawing();
        Utility.changeDrawing(drawing);
    }

    /**
	  * GUI for the minigame
	  */
    public class MemoryGameDrawing extends MinigameDrawing {
        /**
         * Cards
         */
        private Card[][] cards;

        /**
		 * Timer
		 */
        private Timer timer;

        /**
         * Flipped Cards
         */
        private boolean[][] flipped;

        /**
         * Contains Checked Cards
         */
        LinkedList<Card> checked;
        
        /**
		 * Object constructor. Uses the superclass's constructor
		 */
        public MemoryGameDrawing() {
            super();
            cards = new Card[5][2];
            flipped = new boolean[5][2];
            checked = new LinkedList<Card>();
            int[] id = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4};
            String[] names = {"Burger", "Soda", "Fries", "IceCream", "Salad"};
            for(int i = 0; i < id.length; i++) {
                int rand = (int)(Math.random()*id.length);
                int temp = id[rand];
                id[rand] = id[i];
                id[i] = temp;
            }
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < 5; j++) {
                    System.out.print(id[i*5+j]+ " ");
                    cards[j][i] = new Card(names[id[i*5+j]], 60+j*140, 100+i*180, 120, 160);
                }
                System.out.println();
            }
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < 5; j++) {
                    if(cards[j][i].flipped)
                        checked.add(cards[j][i]);
                    cards[j][i].draw(g);
                }
            }
            if(checked.size() == 2) {
                if(checked.getFirst().name.equals(checked.getLast().name)) {
                    checked.getFirst().found = true;
                    checked.getLast().found = true;
                }
                checked.getFirst().flipped = false;
                checked.getLast().flipped = false;
                checked.clear();
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

class Card implements MouseListener {
    /**
     * The card's name
     */
    protected String name;

    /**
     * The card's x-coordinate (top center)
     */
    protected int x_coord;

    /**
     * The card's y-coordinate (top center)
     */
    protected int y_coord;

    /**
     * The card's width
     */
    protected int width;

    /**
     * The card's height
     */
    protected int height;

    /**
     * Flip boolean
     */
    protected boolean flipped;

    /**
     * Found boolean
     */
    protected boolean found;

    /**
     * The card image
     */
    protected Image card;

    /**
     * The card content image
     */
    protected Image content;

    public Card(String n, int x, int y, int w, int h) {
        name = n;
        x_coord = x;
        y_coord = y;
        width = w;
        height = h;
        flipped = false;
        found = false;
        card = Utility.loadImage("Checklist.png", width, height);
        CovidCashier.frame.addMouseListener(this);
    }

    /**
     * Draws the card to the window
     * @param g     The Graphics object to draw on
     */
    public void draw(Graphics g) {
        g.drawImage(card, x_coord, y_coord, null);
        if(flipped || found)
            g.drawString(name, x_coord+20, y_coord+50);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        if(withinCoordinates())
            flipped = true;
    }
    public void mouseReleased(MouseEvent e) {}

    private boolean withinCoordinates() {
		Point pnt = CovidCashier.frame.getMousePosition();
		return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord+width && pnt.y < y_coord+height;
	}
}