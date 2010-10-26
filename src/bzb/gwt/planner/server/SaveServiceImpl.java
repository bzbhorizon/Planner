package bzb.gwt.planner.server;

import javax.jdo.PersistenceManager;

import bzb.gwt.planner.client.CUser;
import bzb.gwt.planner.client.SaveService;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SaveServiceImpl extends RemoteServiceServlet implements
		SaveService {
	
	public String saveUser (CUser user) {
		SUser suser = new SUser(user.getUserAuth(), user.getUsername(), user.getFullName(), user.getHomeCountry(), user.getMale(), user.getAge());
		suser.save();
		return suser.getEncodedUsername();
	}
	
	public String checkUser (String userAuth) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        String encodedKey = null;
		try {
        	encodedKey = KeyFactory.keyToString((KeyFactory.createKey(SUser.class.getSimpleName(), userAuth)));
        	try {
        		pm.getObjectById(SUser.class, encodedKey);
        		return encodedKey;
        	} catch (Exception e) {
           		return null;
        	}
        } finally {
            pm.close();
        }
	}
}
