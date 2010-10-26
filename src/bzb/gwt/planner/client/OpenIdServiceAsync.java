package bzb.gwt.planner.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>OpenIdService</code>.
 */
public interface OpenIdServiceAsync {
	void getOpenIdEndpoint(AsyncCallback<String> callback);
	void verifyAuth(String auth, AsyncCallback<CUser> callback);
}
