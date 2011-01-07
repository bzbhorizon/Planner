package bzb.gwt.planner.client.panels.itinerary;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class TravelPanel extends SimplePanel {
	
	public TravelPanel (String title) {
		setTitle(title);
		
		setWidget(new HTML("Some travel"));
	}

}
