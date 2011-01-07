package bzb.gwt.planner.client.data;

import java.io.Serializable;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.DatastoreServiceAsync;
import bzb.gwt.planner.client.Planner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CTrip implements Serializable {
	
	private static final long serialVersionUID = 8789825455053333830L;
	private long tripId;
    private String encodedUsername;
	private String name;
	private long creationTime;
	
	public CTrip () {
		
	}
	
	public CTrip (String name, String encodedUsername) {
		this();
		setName(name);
		setEncodedUsername(encodedUsername);
	}
	
	public CTrip (String name, String encodedUsername, long tripId, long creationTime) {
		this(name, encodedUsername);
		setTripId(tripId);
		setCreationTime(creationTime);
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

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}
	
	public void delete() {
		Planner.showActivityIndicator();
		((DatastoreServiceAsync)GWT.create(DatastoreService.class)).deleteTrip(this.getTripId(), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				caught.printStackTrace();
				System.out.println("Remote Procedure Call - Failure");
				Planner.hideActivityIndicator();
			}

			public void onSuccess(String result) {
				Planner.updateContent();
				Planner.hideActivityIndicator();
			}
		});
	}

}
