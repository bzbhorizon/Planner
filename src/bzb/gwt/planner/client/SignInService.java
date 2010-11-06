package bzb.gwt.planner.client;


import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("signin")
public interface SignInService extends RemoteService {
	String getOpenIdEndpoint();
	CUser verifyAuth(String auth);
	
	CUser facebookAuth(String clientId, String secret, String authCode);
}
