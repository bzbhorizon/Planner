package bzb.gwt.planner.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.data.CInvitation;
import bzb.gwt.planner.client.data.CInviteeInfo;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.client.data.CInvitationInfo;
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
			List<SInvitation> results2 = (List<SInvitation>) pm.newQuery(SInvitation.class, "encodedUsername == '" + encodedUsername + "'").execute();
			for (SInvitation invitation : results2) {
				if (invitation.isConfirmed() && invitation.getEncodedUsername().equals(encodedUsername)) {
					CTrip trip = pm.getObjectById(STrip.class, invitation.getTripId()).getCTrip();
					if (!trips.contains(trip)) {
						trips.add(trip);
					}
				}
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

	public List<CInviteeInfo> getInviteesFor(long tripId) {
		List<CInviteeInfo> invitees;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SInvitation> results = (List<SInvitation>) pm.newQuery(SInvitation.class).execute();
			
			invitees = new ArrayList<CInviteeInfo>();
			for (SInvitation invitation : results) {
				if (invitation.getTripId().getId() == tripId) {
		        	try {
		        		SUser user = pm.getObjectById(SUser.class, invitation.getEncodedUsername());
		        		invitees.add(new CInviteeInfo(invitation.getCInvitation(), user.getCUser()));
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

	public List<CInvitationInfo> getInvitationsFor(String encodedUsername, boolean openOnly) {
		List<CInvitationInfo> invitations;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<SInvitation> results = (List<SInvitation>) pm.newQuery(SInvitation.class, "encodedUsername == '" + encodedUsername + "'").execute();
			invitations = new ArrayList<CInvitationInfo>();
			for (SInvitation invitation : results) {
				if (openOnly && !invitation.isConfirmed()) {
					STrip trip = pm.getObjectById(STrip.class, invitation.getTripId());
					SUser user = pm.getObjectById(SUser.class, invitation.getEncodedUsername());
					invitations.add(new CInvitationInfo(invitation.getCInvitation(), trip.getCTrip(), user.getCUser()));
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
	        s.setConfirmationTime(System.currentTimeMillis());
	    } finally {
	        pm.close();
	    }
		return s.getEncodedUsername();
	}

	public CTrip checkTrip(long tripId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    STrip s;
		try {
	        s = pm.getObjectById(STrip.class, KeyFactory.createKey(STrip.class.getSimpleName(), tripId));
	    } finally {
	        pm.close();
	    }
		return s.getCTrip();
	}

	public String deleteInvitation(long connectionId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
        	Key key = KeyFactory.createKey(SInvitation.class.getSimpleName(), connectionId);
        	try {
        		pm.deletePersistent(pm.getObjectById(SInvitation.class, key));
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        } finally {
            pm.close();
        }
		return null;
	}
}
