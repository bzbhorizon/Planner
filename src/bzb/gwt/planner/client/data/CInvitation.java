package bzb.gwt.planner.client.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class CInvitation implements Serializable {

	private static final long serialVersionUID = 8613698155779553789L;
	private long connectionId;
	private String encodedUsername;
	private long tripId;
	private long creationTime;
	private boolean confirmed;
	private long confirmationTime;
	
	public CInvitation () {
		
	}
	
	public CInvitation (String encodedUsername, long tripId) {
		setEncodedUsername(encodedUsername);
		setTripId(tripId);
	}

	public void setEncodedUsername(String encodedUsername) {
		this.encodedUsername = encodedUsername;
	}

	public String getEncodedUsername() {
		return encodedUsername;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public long getTripId() {
		return tripId;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmationTime(long confirmationTime) {
		this.confirmationTime = confirmationTime;
	}

	public long getConfirmationTime() {
		return confirmationTime;
	}

	public void setConnectionId(long connectionId) {
		this.connectionId = connectionId;
	}

	public long getConnectionId() {
		return connectionId;
	}

}
