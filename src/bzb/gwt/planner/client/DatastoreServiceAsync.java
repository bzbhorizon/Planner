package bzb.gwt.planner.client;


import java.util.List;

import bzb.gwt.planner.client.data.CInvitation;
import bzb.gwt.planner.client.data.CInviteeInfo;
import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.client.data.CInvitationInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatastoreServiceAsync {
	void saveUser(CUser user, AsyncCallback<String> callback);
	void checkUserByAuth(String userAuth, AsyncCallback<CUser> callback);
	void checkUserByName(String name, AsyncCallback<List<CUser>> callback);
	void checkUserByUsername(String username, AsyncCallback<CUser> callback);
	
	void saveTrip(CTrip trip, AsyncCallback<Long> callback);
	void deleteTrip (long tripId, AsyncCallback<String> callback);
	void getTripsFor(String encodedUsername, AsyncCallback<List<CTrip>> callback);
	void getInviteesFor(long tripId, AsyncCallback<List<CInviteeInfo>> callback);
	
	void sendInvitation(CInvitation invitation, AsyncCallback<Long> callback);
	void getInvitationsFor(String encodedUsername, boolean openOnly, AsyncCallback<List<CInvitationInfo>> callback);
	void acceptInvitation(long connectionId, AsyncCallback<String> callback);
	void checkTrip(long tripId, AsyncCallback<CTrip> callback);
	void deleteInvitation(long connectionId, AsyncCallback<String> callback);
}
