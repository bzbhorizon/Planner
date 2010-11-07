package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.data.CTrip;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class PlanningPanel extends PlannerPanel implements IPlannerPanel {
	
	private CTrip trip;
	
	public PlanningPanel (CTrip trip) {
		this.trip = trip;
	}
	
	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		hp.add(getNavButton(State.TRIP, trip.getName()));
		hp.add(getNavButton(State.PLANNING, "Planning"));
		return hp;
	}
	
}
