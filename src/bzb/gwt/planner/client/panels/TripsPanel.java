package bzb.gwt.planner.client.panels;

import java.util.ArrayList;
import java.util.List;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.data.CTrip;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class TripsPanel extends PlannerPanel implements IPlannerPanel {
	
	List<CTrip> trips;
	
	public void listTrips () {
		clear();
		
		Planner.showActivityIndicator();
		trips = new ArrayList<CTrip>();
		Planner.saveService.getTripsFor(Planner.getUser().getEncodedUsername(),
				new AsyncCallback<List<CTrip>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						caught.printStackTrace();
						System.out
								.println("Remote Procedure Call - Failure");
						Planner.hideActivityIndicator();
					}

					public void onSuccess(List<CTrip> result) {
						trips = result;
						
						final HorizontalPanel hp = new HorizontalPanel();
						final Button newTrip = new Button("Create new trip");
						newTrip.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								newTrip.setEnabled(false);
								
								hp.add(new HTML("Name"));
								
								final TextBox newTripName = new TextBox();
								hp.add(newTripName);
								
								final Button addNewTrip = new Button("Save");
								addNewTrip.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										CTrip trip = new CTrip(newTripName.getText(), Planner.getUser().getEncodedUsername());
										//Planner.getUser().getTrips().add(trip);
										trip.save();
										//listTrips();
									}
								});
								hp.add(addNewTrip);
							}
						});
						hp.add(newTrip);
						add(hp);
						
						if (trips.size() > 0) {
							for (CTrip trip : trips) {
								add(new HTML(trip.getName()));
							}
						} else {
							add (new HTML("No saved trips"));
						}	
						
						Planner.hideActivityIndicator();
					}
				});
	}

	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		return hp;
	}
	
}
