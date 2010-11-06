package bzb.gwt.planner.client;


import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>OpenIdService</code>.
 */
public interface SignInServiceAsync {
	void getOpenIdEndpoint(AsyncCallback<String> callback);
	void verifyAuth(String auth, AsyncCallback<CUser> callback);
	
	void facebookAuth (String clientId, String secret, String authCode, AsyncCallback<CUser> callback);
}
