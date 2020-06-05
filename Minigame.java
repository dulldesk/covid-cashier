/**
  * A minigame
  * 
  * Last edit: 6/3/2020
  * @author 	Eric
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;
import javax.swing.*;

public abstract class Minigame {
    /**
	  * The score of the game
	  */
    protected double score = 0;

    /**
      * Increase the score by an amount
      * @param add      
	  */
    protected void changeScore(double amount) {
        score += amount;
    }

    /**
      * Set the score to an amount
      * @param set      
	  */
      protected void set(double amount) {
        score = amount;
    }

    /**
     * Reset the score to zero
     */
    protected void clear() {
        score = 0;
    }

    /**
     * Get the current score
     * @return score
     */
    protected double getScore() {
        return score;
    }

    public abstract class MinigameDrawing extends JComponent {
        /**
         * Draw the actual display
         * @param g 		the Graphics object to draw on
         */
        public abstract void display(Graphics g);

        /**
		  * Paint method of JComponent
		  * @param g 	the Graphics object to draw on
		  */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				display(g);
			} catch (Exception e) {}
		}
    }
}