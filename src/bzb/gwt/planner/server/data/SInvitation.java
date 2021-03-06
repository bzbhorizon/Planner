package bzb.gwt.planner.server.data;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import bzb.gwt.planner.client.data.CInvitation;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class SInvitation {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key connectionId;
	
	@Persistent
	private String encodedUsername;
	
	@Persistent
	private Key tripId;
	
	@Persistent
	private long creationTime = System.currentTimeMillis();
	
	@Persistent
	private boolean confirmed = false;
	
	@Persistent
	private long confirmationTime;
	
	public SInvitation () {
		
	}
	
	public SInvitation(CInvitation invitation) {
		setConfirmationTime(invitation.getConfirmationTime());
		setConfirmed(invitation.isConfirmed());
		setEncodedUsername(invitation.getEncodedUsername());
		setTripId(KeyFactory.createKey(STrip.class.getSimpleName(), invitation.getTripId()));
	}
	
	public CInvitation getCInvitation () {
		CInvitation invitation = new CInvitation(getEncodedUsername(), getTripId().getId());
		invitation.setCreationTime(getCreationTime());
		invitation.setConfirmationTime(getConfirmationTime());
		invitation.setConfirmed(isConfirmed());
		invitation.setConnectionId(getConnectionId().getId());
		return invitation;
	}

	public void setConnectionId(Key connectionId) {
		this.connectionId = connectionId;
	}

	public Key getConnectionId() {
		return connectionId;
	}

	public void setEncodedUsername(String encodedUsername) {
		this.encodedUsername = encodedUsername;
	}

	public String getEncodedUsername() {
		return encodedUsername;
	}
	
	public void save () {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
	}

	public void setTripId(Key tripId) {
		this.tripId = tripId;
	}

	public Key getTripId() {
		return tripId;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setConfirmationTime(long confirmationTime) {
		this.confirmationTime = confirmationTime;
	}

	public long getConfirmationTime() {
		return confirmationTime;
	}

}
