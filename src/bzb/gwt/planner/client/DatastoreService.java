package bzb.gwt.planner.client;


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
}
