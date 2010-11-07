package bzb.gwt.planner.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.data.CInvitation;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.server.data.PMF;
import bzb.gwt.planner.server.data.SInvitation;
import bzb.gwt.planner.server.data.STrip;
import bzb.gwt.planner.server.data.SUser;

import com.google.appengine.api.datastore.Key;
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
	
	public CUser checkUserByAuth (String userAuth) {
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
	
	public List<CUser> checkUserByName (String name) {
		List<CUser> users;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SUser> results = (List<SUser>) pm.newQuery(SUser.class, "fullName == '" + name + "'").execute();
			users = new ArrayList<CUser>();
			for (SUser user : results) {
				users.add(user.getCUser());
			}
        } finally {
            pm.close();
        }
        return users;
	}
	
	public CUser checkUserByUsername (String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SUser> results = (List<SUser>) pm.newQuery(SUser.class, "username == '" + username + "'").execute();
			for (SUser user : results) {
				return user.getCUser();
			}
        } finally {
            pm.close();
        }
        return null;
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

	public String deleteTrip(long tripId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
        	Key key = KeyFactory.createKey(STrip.class.getSimpleName(), tripId);
        	try {
        		pm.deletePersistent(pm.getObjectById(STrip.class, key));
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        } finally {
            pm.close();
        }
		return null;
	}

	public List<CUser> getInviteesFor(long tripId) {
		List<CUser> invitees;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SInvitation> results = (List<SInvitation>) pm.newQuery(SInvitation.class).execute();
			
			invitees = new ArrayList<CUser>();
			for (SInvitation invitation : results) {
				if (invitation.getTripId().getId() == tripId) {
		        	try {
		        		SUser user = pm.getObjectById(SUser.class, invitation.getEncodedUsername());
		        		invitees.add(user.getCUser());
		        	} catch (Exception e) {
		           		e.printStackTrace();
		        	}
				}
			}
        } finally {
            pm.close();
        }
        return invitees;
	}

	public Long sendInvitation(CInvitation invitation) {
		SInvitation s = new SInvitation(invitation);
		s.save();
		return s.getConnectionId().getId();
	}

	public List<CInvitation> getInvitationsFor(String encodedUsername, boolean openOnly) {
		List<CInvitation> invitations;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SInvitation> results = (List<SInvitation>) pm.newQuery(SInvitation.class, "encodedUsername == '" + encodedUsername + "'").execute();
			invitations = new ArrayList<CInvitation>();
			for (SInvitation invitation : results) {
				if (openOnly && !invitation.isConfirmed()) {
					invitations.add(invitation.getCInvitation());
				}
			}
        } finally {
            pm.close();
        }
        return invitations;
	}

	public String acceptInvitation(long connectionId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    SInvitation s;
		try {
	        s = pm.getObjectById(SInvitation.class, KeyFactory.createKey(SInvitation.class.getSimpleName(), connectionId));
	        s.setConfirmed(true);
	    } finally {
	        pm.close();
	    }
		return s.getEncodedUsername();
	}
}
