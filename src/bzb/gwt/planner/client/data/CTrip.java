package bzb.gwt.planner.client.data;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.DatastoreServiceAsync;
import bzb.gwt.planner.client.Planner;

public class CTrip implements Serializable {
	
	private static final long serialVersionUID = 8789825455053333830L;
	private long tripId;
    private String encodedUsername;
	private String name;
	
	public CTrip () {}
	
	public CTrip (String name, String encodedUsername) {
		setName(name);
		setEncodedUsername(encodedUsername);
	}
	
	public CTrip (String name, String encodedUsername, long tripId) {
		this(name, encodedUsername);
		setTripId(tripId);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public long getTripId() {
		return tripId;
	}

	public void setEncodedUsername(String encodedUsername) {
		this.encodedUsername = encodedUsername;
	}

	public String getEncodedUsername() {
		return encodedUsername;
	}

	public void save() {
		Planner.showActivityIndicator();
		((DatastoreServiceAsync)GWT.create(DatastoreService.class)).saveTrip(this, new AsyncCallback<Long>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				caught.printStackTrace();
				System.out.println("Remote Procedure Call - Failure");
				Planner.hideActivityIndicator();
			}

			public void onSuccess(Long tripId) {
				setTripId(tripId);
				Planner.updateContent();
				Planner.hideActivityIndicator();
			}
		});
	}

}
