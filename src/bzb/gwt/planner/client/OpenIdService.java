package bzb.gwt.planner.client;

import bzb.gwt.planner.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("openid")
public interface OpenIdService extends RemoteService {
	String getOpenIdEndpoint();
	User verifyAuth(String auth);
}
