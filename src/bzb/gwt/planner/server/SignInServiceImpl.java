package bzb.gwt.planner.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

import bzb.gwt.planner.client.SignInService;
import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SignInServiceImpl extends RemoteServiceServlet implements
		SignInService {
	
	private static ConsumerManager manager;
	
	private static final Logger log = Logger.getLogger(SignInServiceImpl.class.getName());
	
	//private static final String RETURN_TO_PATH = "http://127.0.0.1:8888/Planner.html?gwt.codesvr=127.0.0.1:9997&";
	private static final String RETURN_TO_PATH = "http://plannertr.appspot.com/";

	public String getOpenIdEndpoint() {
		String auth = null;
		
		try {
			manager = new ConsumerManager();
		
			List discoveries = manager.discover("https://www.google.com/accounts/o8/id");
			
			if (discoveries != null) {
				DiscoveryInformation discovered = manager.associate(discoveries);
				if (discovered != null) {
					HttpServletRequest request = this.getThreadLocalRequest();
					HttpSession session = request.getSession();
					
					//System.out.println(discovered.getOPEndpoint());
					session.setAttribute("openid-disc", discovered);
					
					// obtain a AuthRequest message to be sent to the OpenID provider
		            AuthRequest authReq = manager.authenticate(discovered, RETURN_TO_PATH + "?state=gauth");

		            // Attribute Exchange example: fetching the 'email' attribute
		            FetchRequest fetch = FetchRequest.createFetchRequest();
		            fetch.addAttribute("email",
		                    // attribute alias
		                    "http://schema.openid.net/contact/email",   // type URI
		                    true);             
		            fetch.addAttribute("FirstName", "http://schema.openid.net/namePerson/first", true);
		            fetch.addAttribute("LastName", "http://schema.openid.net/namePerson/last", true);

		            // attach the extension to the authentication request
		            authReq.addExtension(fetch);
		            
		            auth = authReq.getDestinationUrl(true);
				}
			}
				
			return auth;
		} catch (ConsumerException e) {
			e.printStackTrace();
		} catch (DiscoveryException e) {
			e.printStackTrace();
		} catch (MessageException e) {
			e.printStackTrace();
		}
		
		return auth;
	}
	
	public CUser verifyAuth (String auth) {
		try {
            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            ParameterList response = ParameterList.createFromQueryString(auth.substring(auth.indexOf('?')));

            // retrieve the previously stored discovery information
            DiscoveryInformation discovered = (DiscoveryInformation)
                    this.getThreadLocalRequest().getSession().getAttribute("openid-disc");
            
            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            VerificationResult verification = manager.verify(
                    auth,
                    response, discovered);

            // examine the verification result and extract the verified identifier
            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                AuthSuccess authSuccess =
                        (AuthSuccess) verification.getAuthResponse();

                CUser user = new CUser();
                
                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    FetchResponse fetchResp = (FetchResponse) authSuccess
                            .getExtension(AxMessage.OPENID_NS_AX);

                    if (fetchResp.getAttributeValues("email").get(0) != null) {
                    	user.setUsername((String) fetchResp.getAttributeValues("email").get(0));
                    }
                    if (fetchResp.getAttributeValues("FirstName").get(0) != null || fetchResp.getAttributeValues("LastName").get(0) != null) {
                    	user.setFullName((String) fetchResp.getAttributeValues("FirstName").get(0) + " " + (String) fetchResp.getAttributeValues("LastName").get(0));
                    }
                }
                user.setUserAuth(verified.getIdentifier());
                return user;  // success
            }
        } catch (OpenIDException e) {
            e.printStackTrace();
        }
		
		return null;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	public CUser facebookAuth(String clientId, String secret, String authCode) {
		try {
			String response = Utility.makeGetRequest("https://graph.facebook.com/oauth/access_token?" + 
				"client_id=" + clientId + "&" +
				    "redirect_uri=" + RETURN_TO_PATH + "&" +
				    "client_secret=" + secret + "&" +
				    "code=" + URLEncoder.encode(authCode, "UTF-8"));
			String token = response.substring(0, response.indexOf('&'));
			String infoReq = "https://graph.facebook.com/me?" + token;
			log.warning("a" + infoReq);
			String response2 = Utility.makeGetRequest(infoReq);
			
			JSONObject jo;
			try {
				jo = new JSONObject(response2);
			
				log.warning("b" + jo.toString());
				
				CUser user = new CUser(jo.getString("email"), jo.getString("id"), jo.getString("name"), jo.getJSONObject("location").getString("name"), false, 0, System.currentTimeMillis());
				if (jo.getString("gender").equals("male")) {
					user.setMale(true);
				}
				
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
				try {
					Date date = (Date)formatter.parse(jo.getString("birthday"));
					formatter = new SimpleDateFormat("yyyy");
					user.setAge(Integer.parseInt(formatter.format(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				return user;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		return null;
	}
}
