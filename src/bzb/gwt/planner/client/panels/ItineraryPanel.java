package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.panels.segments.AccommodationSegment;
import bzb.gwt.planner.client.panels.segments.Segment;
import bzb.gwt.planner.client.panels.segments.TravelSegment;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ItineraryPanel extends PlannerPanel implements IPlannerPanel {
	
	private CTrip trip;
	
	public ItineraryPanel (CTrip trip) {
		this.trip = trip;
		
		DockPanel dp = new DockPanel();
		dp.setWidth("100%");
		add(dp);
		
		final AbsolutePanel ap = new AbsolutePanel();
		add(ap);
		ap.setWidth("100%");
		ap.setHeight("600px");
		// initialize our drag controller
	    final PickupDragController dragController = new PickupDragController(ap, true);
	    dragController.setBehaviorMultipleSelection(false);
	    dragController.addDragHandler(new DragHandler() {
			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
				
			}
			public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
				
			}
			public void onDragStart(DragStartEvent event) {
				
			}
			public void onDragEnd(DragEndEvent event) {
				
			}
		});
	    
	    HorizontalPanel addPanel = new HorizontalPanel();
	    Button newTravel = new Button("New travel");
		newTravel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    Segment s = new TravelSegment(dragController);
			    ap.add(s);
			    dragController.makeDraggable(s);
			}
		});
		addPanel.add(newTravel);
	    
		Button newAccom = new Button("New accommodation");
		newAccom.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    Segment s = new AccommodationSegment(dragController);
			    ap.add(s);
			    dragController.makeDraggable(s);
			}
		});
		addPanel.add(newAccom);
		dp.add(addPanel, DockPanel.WEST);
		
		dp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		HorizontalPanel savePanel = new HorizontalPanel();
		Button save = new Button("Save");
		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    System.out.println("Save");
			}
		});
		savePanel.add(save);
		
		Button revert = new Button("Revert");
		revert.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    System.out.println("Revert");
			}
		});
		savePanel.add(revert);
		dp.add(savePanel, DockPanel.EAST);
	}
	
	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		hp.add(getNavButton(State.TRIP, trip.getName()));
		hp.add(getNavButton(State.ITINERARY, "Itinerary"));
		return hp;
	}
	
}
