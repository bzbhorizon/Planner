package bzb.gwt.planner.client;


import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SaveService</code>.
 */
public interface DatastoreServiceAsync {
	void saveUser(CUser user, AsyncCallback<String> callback);
	void checkUser(String userAuth, AsyncCallback<CUser> callback);
}
