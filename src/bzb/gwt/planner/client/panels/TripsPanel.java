package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.data.CTrip;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TripsPanel extends PlannerPanel implements IPlannerPanel {

	public TripsPanel () {
		if (Planner.getUser().getTrips() != null) {
			if (Planner.getUser().getTrips().size() > 0) {
				for (CTrip trip : Planner.getUser().getTrips()) {
					add(new HTML(trip.getName()));
				}
			} else {
				add (new HTML("No trips"));
			}
		} else {
			add (new HTML("No trips"));
		}
	}

	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		return hp;
	}
	
}
