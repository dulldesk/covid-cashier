/**
  * A checklist listing the to-be-completed stations for the training level
  * 
  * Last edit: 6/4/2020
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
	  * @param taskNames the tasks to be completed
	  */
	public Checklist(String [] taskNames) {
		super();
		initializeTasks(taskNames);
	}

	/**
	  * Constructs a Checklist without loading images
	  */
	public Checklist(boolean doNotLoad) {
		this(true,null);
	}

	/**
	  * Constructs a Checklist without loading images
	  */
	public Checklist(boolean doNotLoad, String [] taskNames) {
		super(true);
		initializeTasks(taskNames);
	}

	private void initializeTasks(String [] taskNames) {
		tasks = new TreeMap<String,Boolean>();

		if (taskNames == null) {
			String [] order = {"a","b","c"};
			taskOrder = order;
		} else {
			taskOrder = taskNames;
		}

		loadTasks(taskOrder);
	}

	@Override
	public void drawOpen(Graphics g) {
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
					g.drawLine(x_coord+30+i,y-15,x_coord+40+i,y);
					g.drawLine(x_coord+40+i,y,x_coord+55+i,y-25);
				}

				g.setColor(Color.black);
			}

			g.drawString(name,x,y);
			y += Utility.TEXT_FONT.getSize()+20;
		}
	}

	private void drawTitle(int x, int y, Graphics g) {
		g.setFont(Utility.LABEL_FONT);
		g.setColor(Color.black);
		g.drawString("To Visit",x,y);
	}


	public void loadTasks(String [] taskNames) {
		for (String tsk : taskNames) {
			tasks.put(tsk,false);
		}
	}

	/**
	  * Marks a task as completed
	  * @param task the name of the task
	  */
	public void completeTask(String task) {
		tasks.put(task,true);
	}
}
