package bzb.gwt.planner.client.panels;

import java.util.List;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;
import bzb.gwt.planner.client.Utility;
import bzb.gwt.planner.client.data.CInvitation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HQPanel extends PlannerPanel implements IPlannerPanel {
	
	private static ConfirmDialog cd;
	
	public HQPanel () {
		updateContent();
	}
	
	private void updateContent() {
		add(new HTML("Welcome " + Planner.getUser().getFullName()));
		add(new HTML("Registered since " + Utility.formatDateTime(Planner.getUser().getCreationTime())));
		
		add(new HTML("*** List open invitations"));
		final VerticalPanel invitePanel = new VerticalPanel();
		Planner.showActivityIndicator();
		Planner.datastoreService.getInvitationsFor(Planner.getUser().getEncodedUsername(), true,
				new AsyncCallback<List<CInvitation>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						caught.printStackTrace();
						System.out
								.println("Remote Procedure Call - Failure");
						Planner.hideActivityIndicator();
					}

					public void onSuccess(List<CInvitation> results) {
						if (results != null) {
							for (final CInvitation invitation : results) {
								final Button invitationButton = new Button(invitation.getTripId() + " blah");
								invitationButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										cd = new ConfirmDialog(invitation.getConnectionId());
									}
								});
								invitePanel.add(invitationButton);
							}
						}
						Planner.hideActivityIndicator();
					}
				});
		add(invitePanel);
		
		Button done = new Button();
	    done.setText("Show trips");
	    done.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Planner.updateContent(State.TRIPS);
			}
	    	
	    });
	    add(done);
	}

	class ConfirmDialog extends DialogBox {
		
		public ConfirmDialog (final long connectionId) {
			HorizontalPanel hp = new HorizontalPanel();
			
			final Button accept = new Button("Accept");
			accept.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Planner.showActivityIndicator();
					Planner.datastoreService.acceptInvitation(connectionId,
							new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									caught.printStackTrace();
									System.out
											.println("Remote Procedure Call - Failure");
									Planner.hideActivityIndicator();
								}

								public void onSuccess(String result) {
									Planner.hideActivityIndicator();
									updateContent();
								}
							});
					hide();
				}
			});
			hp.add(accept);
			
			final Button reject = new Button("Cancel");
			reject.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			hp.add(reject);
			
			setWidget(hp);
			
			center();
		}
		
	}
	
	public HorizontalPanel getNav() {
		return getHQNav();
	}
	
}
