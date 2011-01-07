package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.panels.itinerary.ActivityPanel;
import bzb.gwt.planner.client.panels.itinerary.ArriveAccommodationPanel;
import bzb.gwt.planner.client.panels.itinerary.LeaveAccommodationPanel;
import bzb.gwt.planner.client.panels.itinerary.TravelPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ItineraryPanel extends PlannerPanel implements IPlannerPanel {
	
	private CTrip trip;
	private StackPanel sp;
	
	private int accomCount = 0;
	
	public ItineraryPanel (CTrip trip) {
		this.trip = trip;
		
		VerticalPanel vp = new VerticalPanel();
		add(vp);
		
		HorizontalPanel hp = new HorizontalPanel();
		vp.add(hp);
		
		sp = new StackPanel();
		vp.add(sp);
		
		Button newAccom = new Button("New Accommodation");
		newAccom.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sp.add(new ArriveAccommodationPanel("Arrive accom", accomCount));
				sp.add(new LeaveAccommodationPanel("Leave accom", accomCount++));
				resetStackTexts();
			}
		});
		hp.add(newAccom);
		
		Button newTravel = new Button("New Travel");
		newTravel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sp.add(new TravelPanel("Travel"));
				resetStackTexts();
			}
		});
		hp.add(newTravel);
		
		Button newActivity = new Button("New Activity");
		newActivity.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sp.add(new ActivityPanel("Activity"));
				resetStackTexts();
			}
		});
		hp.add(newActivity);
		
		Button moveUp = new Button("Move up");
		moveUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selected = sp.getSelectedIndex();
				if (selected > 0) {
					Widget w = sp.getWidget(selected);
					if (w.getClass().equals(LeaveAccommodationPanel.class) && !(sp.getWidget(selected - 1).getClass().equals(LeaveAccommodationPanel.class) && (((LeaveAccommodationPanel)sp.getWidget(selected - 1)).getId() == ((LeaveAccommodationPanel)w).getId()))) {
						sp.remove(selected);
						sp.insert(w, selected - 1);
						sp.showStack(selected - 1);
						resetStackTexts();
					}
				}
			}
		});
		hp.add(moveUp);
		
		Button moveDown = new Button("Move down");
		moveDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selected = sp.getSelectedIndex();
				if (selected < sp.getWidgetCount() - 1) {
					Widget w = sp.getWidget(selected);
					sp.remove(selected);
					sp.insert(w, selected + 1);
					sp.showStack(selected + 1);
					resetStackTexts();
				}
			}
		});
		hp.add(moveDown);
	}
	
	private void resetStackTexts () {
		for (int i = 0; i < sp.getWidgetCount(); i++) {
			sp.setStackText(i, sp.getWidget(i).getTitle());
		}
	}
	
	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		hp.add(getNavButton(State.TRIP, trip.getName()));
		hp.add(getNavButton(State.ITINERARY, "Itinerary"));
		return hp;
	}
	
}
