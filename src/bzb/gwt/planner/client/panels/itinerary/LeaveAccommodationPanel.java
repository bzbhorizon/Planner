package bzb.gwt.planner.client.panels.itinerary;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class LeaveAccommodationPanel extends SimplePanel {
	
	private int id;
	
	public LeaveAccommodationPanel (String title, int id) {
		setTitle(title);
		setId(id);
		
		setWidget(new HTML("Leave accommodation"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
