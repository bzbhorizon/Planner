package bzb.gwt.planner.client.panels;

import java.util.ArrayList;
import java.util.List;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.Utility;
import bzb.gwt.planner.client.data.CInvitation;
import bzb.gwt.planner.client.data.CInviteeInfo;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TripsPanel extends PlannerPanel implements IPlannerPanel {
	
	private List<CTrip> trips;
	private CTrip trip;
	
	private static DeleteDialog dd;
	private static InviteDialog id;
	
	public void listTrips () {
		clear();
		
		Planner.showActivityIndicator();
		
		final VerticalPanel vp = new VerticalPanel();
		add(vp);
		
		trips = new ArrayList<CTrip>();
		Planner.datastoreService.getTripsFor(Planner.getUser().getEncodedUsername(),
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
						hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
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
						vp.add(hp);
						
						if (trips.size() > 0) {
							for (final CTrip trip : trips) {
								Button tripButton = new Button(trip.getName());
								if (!trip.getEncodedUsername().equals(Planner.getUser().getEncodedUsername())) {
									tripButton.setText(tripButton.getText() + " (invited)");
								}
								tripButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										addNavButton(new Button(trip.getName()));
										describeTrip(trip);	
									}
								});
								vp.add(tripButton);
							}
						} else {
							vp.add (new HTML("No saved trips"));
						}	
						
						Planner.hideActivityIndicator();
					}
				});
	}
	
	private void describeTrip (final CTrip trip) {
		this.trip = trip;
		
		Planner.setState(State.TRIP);
		
		clear();
		
		add(new HTML("Trip: " + trip.getName()));
		add(new HTML("Started planning at " + Utility.formatDateTime(trip.getCreationTime())));
		
		Button delete = new Button("Delete trip");
		if (Planner.getUser().getEncodedUsername().equals(trip.getEncodedUsername())) {
			delete.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					dd = new DeleteDialog(trip);
				}
			});
		} else {
			delete.setEnabled(false);
		}
		add(delete);
		
		Button edit = new Button("Itinerary");
		edit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Planner.setTrip(trip);
				Planner.updateContent(State.ITINERARY);
			}
		});
		add(edit);
		
		add(new HTML("Travellers on trip:"));
		
		final VerticalPanel inviteesPanel = new VerticalPanel();
		
		Planner.showActivityIndicator();
		Planner.datastoreService.getInviteesFor(trip.getTripId(),
				new AsyncCallback<List<CInviteeInfo>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						caught.printStackTrace();
						System.out
								.println("Remote Procedure Call - Failure");
						Planner.hideActivityIndicator();
					}

					public void onSuccess(List<CInviteeInfo> results) {
						if (results != null) {
							for (final CInviteeInfo invitee : results) {
								HorizontalPanel inviteePanel = new HorizontalPanel();
								
								String buttonText = invitee.getInvitee().getFullName() + " ";
								if (invitee.getInvitation().isConfirmed()) {
									buttonText += "(accepted invite at " + Utility.formatDateTime(invitee.getInvitation().getConfirmationTime()) + ")";
								} else {
									buttonText += "(invitation sent at " + Utility.formatDateTime(invitee.getInvitation().getCreationTime()) + " not yet accepted)";
								}
								final Button userButton = new Button(buttonText);
								userButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										Planner.showUpdFor(invitee.getInvitee().getUsername());
									}
								});
								inviteePanel.add(userButton);
								
								final Button deleteUser = new Button("Cancel invitation");
								if (Planner.getUser().getEncodedUsername().equals(trip.getEncodedUsername())) {
									deleteUser.addClickHandler(new ClickHandler() {
										public void onClick(ClickEvent event) {
											Planner.datastoreService.deleteInvitation(invitee.getInvitation().getConnectionId(), new AsyncCallback<String>() {
												public void onFailure(
														Throwable caught) {
													caught.printStackTrace();
													System.out
															.println("Remote Procedure Call - Failure");
												}
												public void onSuccess(String result) {
													describeTrip(trip);
												}
											});
										}
									});
								} else {
									deleteUser.setEnabled(false);
								}
								inviteePanel.add(deleteUser);
								
								inviteesPanel.add(inviteePanel);
							}
						}
						Planner.hideActivityIndicator();
					}
				});
		add(inviteesPanel);
		
		Button invite = new Button("Invite another traveller");
		if (Planner.getUser().getEncodedUsername().equals(trip.getEncodedUsername())) {
			invite.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					id = new InviteDialog();
				}
			});
		} else {
			invite.setEnabled(false);
		}
		add(invite);
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
			
			setGlassEnabled(true);
			
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
	
	private class InviteDialog extends DialogBox {
		
		public InviteDialog () {
			setText("Invitation");
			
			setGlassEnabled(true);
			
			VerticalPanel vp = new VerticalPanel();
			
			final VerticalPanel inviteesPanel = new VerticalPanel();
			
			HorizontalPanel hp = new HorizontalPanel();
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			hp.add(new HTML("Name or email address:"));
			final TextBox nameBox = new TextBox();
			hp.add(nameBox);
			final Button search = new Button("Search");
			search.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					inviteesPanel.clear();
					
					Planner.showActivityIndicator();
					Planner.datastoreService.checkUserByName(nameBox.getText(), 
							new AsyncCallback<List<CUser>>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									caught.printStackTrace();
									System.out
											.println("Remote Procedure Call - Failure");
									Planner.hideActivityIndicator();
								}

								public void onSuccess(List<CUser> results) {
									if (results != null) {
										for (final CUser user : results) {
											if (!user.getEncodedUsername().equals(Planner.getUser().getEncodedUsername())) {
												final HorizontalPanel inviteePanel = new HorizontalPanel();
												
												final Button profileButton = new Button(user.getFullName());
												profileButton.addClickHandler(new ClickHandler() {
													public void onClick(ClickEvent event) {
														Planner.showUpdFor(user.getUsername());
													}
												});
												inviteePanel.add(profileButton);
												
												final Button pickButton = new Button("Invite");
												pickButton.addClickHandler(new ClickHandler() {
													public void onClick(ClickEvent event) {
														Planner.showActivityIndicator();
														CInvitation invitation = new CInvitation(user.getEncodedUsername(), trip.getTripId());
														Planner.datastoreService.sendInvitation(invitation, new AsyncCallback<Long>() {
															public void onFailure(
																	Throwable caught) {
																// Show the RPC error message to the user
																caught.printStackTrace();
																System.out
																		.println("Remote Procedure Call - Failure");
																Planner.hideActivityIndicator();
															}
	
															public void onSuccess(
																	Long result) {
																hide();
																Planner.hideActivityIndicator();
																describeTrip(trip);
															}
														});
													}
												});
												
												Planner.datastoreService.getInviteesFor(trip.getTripId(),
														new AsyncCallback<List<CInviteeInfo>>() {
														public void onFailure(Throwable caught) {
															// Show the RPC error message to the user
															caught.printStackTrace();
															System.out
																	.println("Remote Procedure Call - Failure");
															Planner.hideActivityIndicator();
														}

														public void onSuccess(List<CInviteeInfo> results) {
															for (CInviteeInfo invitee : results) {
																if (invitee.getInvitee().getUsername().equals(user.getUsername())) {
																	pickButton.setEnabled(false);
																	pickButton.setText("Already invited");
																}
															}
														}
												});
																
												inviteePanel.add(pickButton);
												
												inviteesPanel.add(inviteePanel);
											}
										}
									}
									Planner.hideActivityIndicator();
								}
							});
					
					Planner.showActivityIndicator();
					Planner.datastoreService.checkUserByUsername(nameBox.getText(), 
							new AsyncCallback<CUser>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									caught.printStackTrace();
									System.out
											.println("Remote Procedure Call - Failure");
									Planner.hideActivityIndicator();
								}

								public void onSuccess(final CUser result) {
									if (result != null) {
										final Button userButton = new Button(result.getFullName());
										userButton.addClickHandler(new ClickHandler() {
											public void onClick(ClickEvent event) {
												Planner.showActivityIndicator();
												CInvitation invitation = new CInvitation(result.getEncodedUsername(), trip.getTripId());
												Planner.datastoreService.sendInvitation(invitation, new AsyncCallback<Long>() {
													public void onFailure(
															Throwable caught) {
														// Show the RPC error message to the user
														caught.printStackTrace();
														System.out
																.println("Remote Procedure Call - Failure");
														Planner.hideActivityIndicator();
													}

													public void onSuccess(
															Long result) {
														hide();
														Planner.hideActivityIndicator();
														describeTrip(trip);
													}
												});
											}
										});
										inviteesPanel.add(userButton);
									}
									Planner.hideActivityIndicator();
								}
							});
				}
			});
			hp.add(search);
			
			vp.add(hp);
			vp.add(inviteesPanel);
			
			final Button cancel = new Button("Cancel");
			cancel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			vp.add(cancel);
			
			setWidget(vp);
			
			center();
		}
	}
	
}
