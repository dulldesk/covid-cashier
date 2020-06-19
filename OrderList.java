/**
  * Holds the orders that the user must fulfil in the live level
  * 
  * <p> Usage:
  * <p> 
  * Declare and initialize an OrderList object in the Restaurant. There should only be one. 
  * When a customer comes in and orders something, call addOrder(String [] tasks) to add the order to the list
  * To complete a task on the order, call completeTask(int id, String task)
  * Check if an order has been completed via isOrderFinished(int id)
  * If an order has been finished, it can be removed via removeOrder(int id)
  * 
  * Check if the entire list of orders has been completed via isFinished()
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste
  * @version 	1.0
  * @since 		1.1
  */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class OrderList extends TaskList {
	/**
	  * All orders to be completed
	  */
	public ArrayList<Checklist> orders;

	/**
	  * Current page viewed on the order list
	  */
	public int pageNo;

	/**
	  * Image tabs
	  */
	private Image [] tabs;

	/**
	  * Constructs a checklist
	  */
	private ArrayList<Button> headerBtns;

	/**
	  * Constructs a checklist
	  */
	public OrderList() {
		super();

		tabs = new Image[2];
		for (int i=0;i<tabs.length;i++) tabs[i] = Utility.loadImage("order_tab_"+i+".png",75,20);

		pageNo = 0;
		orders = new ArrayList<Checklist>();
		headerBtns = new ArrayList<Button>();

		// addOrder(null);
		// addOrder(null);
	}

	/** 
	  * Draws an opened task list (i.e. displays the tasks on screen)
	  * @param g 	the Graphics object to draw on
	  */
	@Override
	protected void drawOpen(Graphics g) {
		if (orders.size() == 0) {
			String msg = "No orders present";
			g.setFont(Utility.TEXT_FONT);
			g.drawString(msg, (Utility.FRAME_WIDTH - Utility.getStringWidth(msg,g))/2, 100);
		} else {
			orders.get(pageNo).drawOpen(g);
			drawHeader(g);
		}
	}

	/**
	  * Draws the tabs so the user can switch between order lists
	  * @param g 	the Graphics object to draw on
	  */
	private void drawHeader(Graphics g) {
		int tabNo = orders.size();

		for (int x = x_coord + 30, ind = 0; x < x_coord + 30 + tabNo*(tabs[0].getWidth(null)+15);x += tabs[0].getWidth(null)+15, ind++) {
			g.drawImage(ind == pageNo ? tabs[0] : tabs[1], x, y_coord + height - 50, null);
			
			Button btn = headerBtns.get(ind);
			btn.draw(g);
			// to-do: fix button hovering/clicking

			if (btn.isClicked()) {
				int btnPage = Integer.parseInt(btn.getName().substring(6))-1;
				if (pageNo != btnPage) {
					pageNo = btnPage;
					CovidCashier.frame.repaint();
					return;
				}
			}

		}
	}

	/**
	  * Add an order of tasks to be completed
	  * @param tasks the name of the tasks to be completed for this order
	  */
	public void addOrder(String [] tasks) {
		notInitialEmpty = true;

		orders.add(new Checklist(true,tasks));
		headerBtns.add(new Button("Order "+orders.size(), x_coord+40 + (orders.size()-1)*(tabs[0].getWidth(null)+15), y_coord+height-37, Utility.TEXT_FONT_SMALL, Color.black, Color.blue));
	} 

	/**
	  * Remove an order
	  * @param id the id of this order (i.e. its index)
	  */
	public void removeOrder(int id) {
		if (orders.size() <= id) return;
		orders.remove(id);
		headerBtns.remove(id);
	}

	/**
	  * Checks if an order is complete
	  * @param id the id of the order to be checked (i.e. its index)
	  * @return whether the order of the given id has been completed, or false if it doesn't exist
	  */
	public boolean isOrderFinished(int id) {
		return orders.size() > id && orders.get(id).isFinished();
	}

	/**
	  * Checks if all orders in the OrderList have been completed
	  * @return whether all orders have been completed
	  */
	public boolean isFinished() {
		for (Checklist list : orders) {
			if (!list.isFinished()) return false;
		}
		return notInitialEmpty;
	}

	/**
	  * Marks a task in a given order as completed
	  * @param id the id of the order to be checked (i.e. its index)
	  * @param task the name of the task
	  */
	public void completeTask(int id, String task) {
		orders.get(id).completeTask(task);
	}

	@Override
	protected void openActivate() {
		for (Button btn : headerBtns) btn.activate();
	}

	@Override
	protected void closeDeactivate() {
		for (Button btn : headerBtns) btn.deactivate();
	}
}
