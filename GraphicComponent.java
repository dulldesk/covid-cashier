/**
  * A graphic component
  * 
  * Last edit: 5/28/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.0
  */

import java.awt.*;

public abstract class GraphicComponent {
	/**
	  * The field's x-coordinate (top left corner)
	  */
	protected int x_coord;

	/**
	  * The field's y-coordinate (top left corner)
	  */
	protected int y_coord;

	/**
	  * The field's width
	  */
	protected int width;

	/**
	  * The field's height
	  */
	protected int height;

	/**
	  * The Font of the button's label
	  */
	protected Font text_font;

	/**
	  * Draws the component
	  * @param g 		the Graphics object to draw on
	  */
	public abstract void draw(Graphics g);

	/**
	  * Activates the component's listeners and the like
	  */
	public abstract void activate();

	/**
	  * Deativates the components's listeners and the like
	  */
	public abstract void deactivate();
}
