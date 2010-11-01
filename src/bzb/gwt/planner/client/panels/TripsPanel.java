package bzb.gwt.planner.client.panels;

import java.util.ArrayList;
import java.util.List;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.Utility;
import bzb.gwt.planner.client.data.CTrip;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TripsPanel extends PlannerPanel implements IPlannerPanel {
	
	private List<CTrip> trips;
	
	private static DeleteDialog dd;
	
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
										trip.save();
									}
								});
								hp.add(addNewTrip);
							}
						});
						hp.add(newTrip);
						add(hp);
						
						if (trips.size() > 0) {
							for (final CTrip trip : trips) {
								Button tripButton = new Button(trip.getName());
								tripButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										describeTrip(trip);	
									}
								});
								add(tripButton);
							}
						} else {
							add (new HTML("No saved trips"));
						}	
						
						Planner.hideActivityIndicator();
					}
				});
	}
	
	private void describeTrip (final CTrip trip) {
		clear();
		
		addNavButton(new Button(trip.getName()));
		
		add(new HTML("Trip: " + trip.getName()));
		add(new HTML("Started planning at " + Utility.formatDateTime(trip.getCreationTime())));
		
		Button delete = new Button("Delete trip");
		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dd = new DeleteDialog(trip);
			}
		});
		add(delete);
		
		Button invite = new Button("Invite another traveller");
		invite.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// lookup invitee
					// if registered, say, if not, say
			}
		});
		add(invite);
		add(new HTML("*** List invitees"));
	}

	public HorizontalPanel getNav() {
		HorizontalPanel hp = getHQNav();
		hp.add(getNavButton(State.TRIPS, "Trips"));
		return hp;
	}
	
	private class DeleteDialog extends DialogBox {
		private CTrip trip;
		
		public DeleteDialog (CTrip trip) {
			setTrip(trip);
			
			setText("Confirm delete");
			
			VerticalPanel vp = new VerticalPanel();
			vp.add(new HTML("Are you sure?"));
			
			HorizontalPanel hp = new HorizontalPanel();
			Button confirm = new Button("Yes, delete");
			confirm.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
					getTrip().delete();
				}
			});
			hp.add(confirm);
			
			Button cancel = new Button("No, keep");
			cancel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			hp.add(cancel);
			
			vp.add(hp);
			setWidget(vp);
			
			center();
		}

		public void setTrip(CTrip trip) {
			this.trip = trip;
		}

		public CTrip getTrip() {
			return trip;
		}
	}
	
}
