package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class HeadNavPanel extends HorizontalPanel {
	
	public HeadNavPanel () {
		if (Planner.getUser() != null) {
			add(new HTML(Planner.getUser().getUsername()));
		} else {
			add(new HTML("Guest"));
		}
		
		add(new HTML(">"));
		
		if (Planner.getState() == State.WELCOME) {
			add(new HTML("Welcome nav"));
		} else if (Planner.getState() == State.TRIPS) {
			add(new HTML("Trips nav"));
		} else {
			add(new HTML("Shouldn't be here"));
		}
		
	}

}
