/**
  * A checklist listing the to-be-completed stations for the training level
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Checklist extends TaskList {
	/**
	  * All tasks of the restaurant, complete or incomplete. 
	  * 
	  * <p> 
	  * The key is the task name with a capital letter of the alphabet prepended to it. 
	  * This is used to define the order of the tasks on the display. It is assumed that less than 26 tasks will exist. 
	  * <p>
	  * The value is whether the task has been completed. 
	  */
	private Map<String,Boolean> tasks;

	/**
	  * Task names, ordered in the order to be displayed in the checklist
	  */
	private String [] taskOrder;

	/**
	  * Constructs a Checklist with the default task names
	  */
	public Checklist() {
		this(null);
	}

	/**
	  * Constructs a Checklist
	  * @param taskNames the name of the tasks to be completed
	  */
	public Checklist(String [] taskNames) {
		super();
		initializeTasks(taskNames);
	}

	/**
	  * Constructs a Checklist without loading images
	  * @param doNotLoad whether or not to load images (e.g. checklist background)
	  */
	public Checklist(boolean doNotLoad) {
		this(true,null);
	}

	/**
	  * Constructs a Checklist without loading images
	  * @param doNotLoad whether or not to load images (e.g. checklist background)
	  * @param taskNames the name of the tasks to be completed
	  */
	public Checklist(boolean doNotLoad, String [] taskNames) {
		super(true);
		initializeTasks(taskNames);
	}

	/**
	  * Initialize the tasks to be completed and displayed in the Checklist
	  * @param taskNames the name of the tasks to be completed
	  */
	private void initializeTasks(String [] taskNames) {
		tasks = new TreeMap<String,Boolean>();
		notInitialEmpty = true;

		if (taskNames == null) {
			taskOrder = new String[]{"a","b","c"};
		} else {
			taskOrder = taskNames;
		}

		for (String tsk : taskNames) {
			tasks.put(tsk,false);
		}
	}

	/** 
	  * Draws an opened task list (i.e. displays the tasks on screen)
	  * @param g 	the Graphics object to draw on
	  */
	@Override
	protected void drawOpen(Graphics g) {
		int x = x_coord + 70;
		int y = y_coord + 60;

		drawTitle(x,y,g);
		y += Utility.LABEL_FONT.getSize()+20;

		g.setFont(Utility.TEXT_FONT);
		g.setColor(Color.black);

		for (String name : taskOrder) {
			if (tasks.get(name)) {
				// Checkmark
				g.setColor(new Color(10,100,10));

				// Loop thickens the line
				for (int i=0;i<3;i++) {
					g.drawLine(x-40+i,y-15,x-30+i,y);
					g.drawLine(x-30+i,y,x-15+i,y-25);
				}

				g.setColor(Color.black);
			}

			g.drawString(name,x,y);
			y += Utility.TEXT_FONT.getSize()+20;
		}
	}

	/**
	  * Draw the checklist title
	  * @param g 	the Graphics object to draw on
	  * @param x 	the x-coordinate of the title
	  * @param y 	the y-coordinate of the title
	  */
	private void drawTitle(int x, int y, Graphics g) {
		g.setFont(Utility.LABEL_FONT);
		g.setColor(Color.black);
		g.drawString("To Visit",x,y);
	}

	/**
	  * Marks a task as completed
	  * @param task the name of the task
	  */
	public void completeTask(String task) {
		tasks.put(task,true);
	}

	/**
	  * @return whether all tasks have been completed
	  */
	public boolean isFinished() {
		for (String key : tasks.keySet()) {
			if (!tasks.get(key)) return false;
		}
		return true;
	}
}
