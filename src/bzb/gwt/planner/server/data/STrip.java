package bzb.gwt.planner.server.data;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import bzb.gwt.planner.client.data.CTrip;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class STrip {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key tripId;
	
	@Persistent
	private String encodedUsername;
	
	@Persistent
	private String name;
	
	public STrip () {
		
	}
	
	public STrip (CTrip trip) {
		setEncodedUsername(trip.getEncodedUsername());
		setName(trip.getName());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTripId(Key tripId) {
		this.tripId = tripId;
	}

	public Key getTripId() {
		return tripId;
	}
	
	public CTrip getCTrip () {
		return new CTrip(getName(), getEncodedUsername(), getTripId().getId());
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

}
