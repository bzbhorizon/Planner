package bzb.gwt.planner.client;


import java.util.List;

import bzb.gwt.planner.client.data.CInvitation;
import bzb.gwt.planner.client.data.CInviteeInfo;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.client.data.CInvitationInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("save")
public interface DatastoreService extends RemoteService {
	String saveUser(CUser user);
	CUser checkUserByAuth(String userAuth);
	List<CUser> checkUserByName(String name);
	CUser checkUserByUsername(String username);
	
	Long saveTrip(CTrip trip);
	String deleteTrip(long tripId);
	CTrip checkTrip(long tripId);
	List<CTrip> getTripsFor(String encodedUsername);
	List<CInviteeInfo> getInviteesFor(long tripId);
	
	Long sendInvitation(CInvitation invitation);
	List<CInvitationInfo> getInvitationsFor (String encodedUsername, boolean openOnly);
	String acceptInvitation (long connectionId);
	String deleteInvitation (long connectionId);
}
