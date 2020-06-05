/**
  * Holds the orders that the user must fulfil in the live level
  * 
  * Last edit: 6/4/2020
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
	private ArrayList<Checklist> orders;

	/**
	  * Current page viewed on the order list
	  */
	private int pageNo;

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

		addOrder(null);
		addOrder(null);
	}

	@Override
	public void drawOpen(Graphics g) {
		if (orders.size() == 0) {
			String msg = "No orders to complete!";
			g.setFont(Utility.TEXT_FONT);
			g.drawString(msg, (Utility.FRAME_WIDTH - Utility.getStringWidth(msg,g))/2, 100);
		} else {
			orders.get(pageNo).drawOpen(g);
			drawHeader(g);
		}
	}

	/**
	  * Draws the tabs so the user can switch between order lists
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

	public void addOrder(String [] tasks) {
		orders.add(new Checklist(true,tasks));
		headerBtns.add(new Button("Order "+orders.size(), x_coord+40 + (orders.size()-1)*(tabs[0].getWidth(null)+15), y_coord+height-37, Utility.TEXT_FONT_SMALL, Color.black, Color.blue));
	} 

	public void removeOrder(int id) {
		orders.remove(id);
		headerBtns.remove(id);
	}

	@Override
	public void openActivate() {
		for (Button btn : headerBtns) btn.activate();
	}

	@Override
	public void closeDeactivate() {
		for (Button btn : headerBtns) btn.deactivate();
	}
}
