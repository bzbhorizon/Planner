package bzb.gwt.planner.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SaveService</code>.
 */
public interface SaveServiceAsync {
	void saveUser(CUser user, AsyncCallback<String> callback);
	void checkUser(String userAuth, AsyncCallback<String> callback);
}
