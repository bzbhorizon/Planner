package bzb.gwt.planner.client.panels.itinerary;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class ArriveAccommodationPanel extends SimplePanel {
	
	private int id;
	
	public ArriveAccommodationPanel (String title, int id) {
		setTitle(title);
		setId(id);
		
		setWidget(new HTML("Arrive at accommodation"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
