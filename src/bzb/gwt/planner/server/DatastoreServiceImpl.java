package bzb.gwt.planner.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.server.data.PMF;
import bzb.gwt.planner.server.data.STrip;
import bzb.gwt.planner.server.data.SUser;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatastoreServiceImpl extends RemoteServiceServlet implements
		DatastoreService {
	
	public String saveUser (CUser user) {
		SUser suser = new SUser(user);
		suser.save();
		return suser.getEncodedUsername();
	}
	
	public CUser checkUser (String userAuth) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        String encodedKey = null;
		try {
        	encodedKey = KeyFactory.keyToString((KeyFactory.createKey(SUser.class.getSimpleName(), userAuth)));
        	try {
        		SUser user = pm.getObjectById(SUser.class, encodedKey);
        		return user.getCUser();
        	} catch (Exception e) {
           		return null;
        	}
        } finally {
            pm.close();
        }
	}

	public Long saveTrip(CTrip trip) {
		STrip strip = new STrip(trip);
		strip.save();
		return strip.getTripId().getId();
	}
	
	public List<CTrip> getTripsFor (String encodedUsername) {
		List<CTrip> trips;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<STrip> results = (List<STrip>) pm.newQuery(STrip.class, "encodedUsername == '" + encodedUsername + "'").execute();
			trips = new ArrayList<CTrip>();
			for (STrip trip : results) {
				trips.add(trip.getCTrip());
			}
        } finally {
            pm.close();
        }
        return trips;
	}
}
