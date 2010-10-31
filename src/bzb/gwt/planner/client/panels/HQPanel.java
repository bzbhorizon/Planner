package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class HQPanel extends PlannerPanel implements IPlannerPanel {

	public HQPanel () {
		add(new HTML("Welcome " + Planner.getUser().getFullName()));
		
		Button done = new Button();
	    done.setText("Show trips");
	    done.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Planner.updateContent(State.TRIPS);
			}
	    	
	    });
	    add(done);
	}

	public HorizontalPanel getNav() {
		return getHQNav();
	}
	
}
