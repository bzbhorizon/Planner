package bzb.gwt.planner.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("openid")
public interface OpenIdService extends RemoteService {
	String getOpenIdEndpoint();
	CUser verifyAuth(String auth);
}
