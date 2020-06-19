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
import java.io.*;

public class MemoryGame extends Minigame {
    /**
	  * Initializes and displays the drawing to the frame
	  */
    public MemoryGame() {
        infoCard = new Dialogue("Memory! As a cashier, you must be able to remember customers' orders. Click cards to flip over and match pairs. Good luck!", "Coworker");
        drawing = new MemoryGameDrawing();
        Utility.changeDrawing(drawing);
        bgm = new BGM("memory");
        bgm.play();
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
        private ArrayList<Card> flipped;

        /**
         * Number of Flipped Cards
         */
        private int count;

        /**
         * ---
         */
        private int delay;

        /**
         * ---
         */
        private int spacing;
        
        /**
		 * Object constructor. Uses the superclass's constructor
		 */
        public MemoryGameDrawing() {
            super();
            cards = new Card[5][2];
            flipped = new ArrayList<Card>();
            count = 0;
            delay = 1000;
            spacing = 0;
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
                    cards[j][i] = new Card(names[id[i*5+j]], i*5+j, 60+j*140, 100+i*180, 120, 160);
                    cards[j][i].activate();
                }
                System.out.println();
            }
            infoCard.activate();
        }

        /**
		  * GUI display of the minigame screen
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void display(Graphics g) {
            g.drawImage(Utility.loadImage("FrontCounterBG.png",Utility.FRAME_WIDTH,Utility.FRAME_HEIGHT),0,0,null);
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < 5; j++) {
                    if(cards[j][i].flipped && !flipped.contains(cards[j][i])) {
                        flipped.add(cards[j][i]);
                        count++;
                    }
                }
            }
            if(count >= 2) {
                for(int i = 0; i < 2; i++) {
                    for(int j = 0; j < 5; j++) {
                        cards[j][i].deactivate();
                    }
                }
                spacing++;
                if(spacing > delay) {
                    if(flipped.get(0).name.equals(flipped.get(1).name)) {
                        flipped.get(0).found = true;
                        flipped.get(1).found = true;
                        score++;
                    }
                    flipped.get(0).flipped = false;
                    flipped.get(1).flipped = false;
                    spacing = 0;
                    count = 0;
                    flipped.clear();
                    for(int i = 0; i < 2; i++) {
                        for(int j = 0; j < 5; j++) {
                            if (!cards[j][i].found) cards[j][i].activate();
                        }
                    }
                }
            }
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < 5; j++) {
                    cards[j][i].draw(g);
                }
            }
            if(spacing < delay && start) {
                spacing++;
                infoCard.draw(g);
                if(infoCard.canProceed) {
                    spacing = delay;
                }
                for(int i = 0; i < 2; i++) {
                    for(int j = 0; j < 5; j++) {
                        cards[j][i].deactivate();
                    }
                }
            } else {
                start = false;
                delay = 25;
                for(int i = 0; i < 2; i++) {
                    for(int j = 0; j < 5; j++) {
                        if (!cards[j][i].found) cards[j][i].activate();
                    }
                }
                if (!end) infoCard.deactivate();
            }
            if(score == 5 && !end) {
                end = true;
                for(int i = 0; i < 2; i++) {
                    for(int j = 0; j < 5; j++) {
                        cards[j][i].deactivate();
                    }
                }
                infoCard = new Dialogue("Congratulations on completing Memory! It's helpful to have a good memory, no matter what you do. Now get back to work!", "Coworker");
                infoCard.activate();
            }
            if(end) {
                infoCard.draw(g);
                if(infoCard.isEntered()) {
                    bgm.stop();
                    infoCard.deactivate();
                    Utility.backToRestaurant();
                    return;
                }
            }
            else {
                g.setColor(Color.black);
                g.setFont(Utility.LABEL_FONT);
                g.drawString("Pairs Found: "+score, Utility.FRAME_WIDTH/2-Utility.getStringWidth("Pairs Found: 0", Utility.LABEL_FONT)/2, 50);
                refreshScreen();
            }
        }

        public void refreshScreen() {
			timer = new Timer(17, new ActionListener() {
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
     * The card's name
     */
    protected final int ID;

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

    /**
     * ---
     */
    private boolean canClick;

    public Card(String n, int i, int x, int y, int w, int h) {
        name = n;
        ID = i;
        x_coord = x;
        y_coord = y;
        width = w;
        height = h;
        flipped = false;
        found = false;
        canClick = false;
        card = Utility.loadImage("Checklist.png", width, height);
        content = Utility.loadImage(name+".png", height/2, height/2);
        CovidCashier.frame.addMouseListener(this);
    }

    /**
     * Draws the card to the window
     * @param g     The Graphics object to draw on
     */
    public void draw(Graphics g) {
        g.drawImage(card, x_coord, y_coord, null);
        if(flipped || found)
            g.drawImage(content, x_coord+width/2-height/4, y_coord+height/4, null);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        if(withinCoordinates() && canClick && !found)
            flipped = true;
    }
    public void mouseReleased(MouseEvent e) {}

    private boolean withinCoordinates() {
		Point pnt = CovidCashier.frame.getMousePosition();
		return pnt.x > x_coord && pnt.y > y_coord && pnt.x < x_coord+width && pnt.y < y_coord+height;
    }
    public void activate() {
        canClick = true;
    }
    public void deactivate() {
        canClick = false;
    }
}