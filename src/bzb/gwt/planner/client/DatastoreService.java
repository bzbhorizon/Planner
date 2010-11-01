package bzb.gwt.planner.client;


import java.util.List;

import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("save")
public interface DatastoreService extends RemoteService {
	String saveUser(CUser user);
	CUser checkUser(String userAuth);
	
	Long saveTrip(CTrip trip);
	String deleteTrip(long tripId);
	List<CTrip> getTripsFor(String encodedUsername);
}
