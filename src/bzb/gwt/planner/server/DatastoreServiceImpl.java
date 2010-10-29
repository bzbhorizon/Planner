package bzb.gwt.planner.server;

import javax.jdo.PersistenceManager;

import bzb.gwt.planner.client.DatastoreService;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.server.data.PMF;
import bzb.gwt.planner.server.data.SUser;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatastoreServiceImpl extends RemoteServiceServlet implements
		DatastoreService {
	
	public String saveUser (CUser user) {
		SUser suser = new SUser(user.getUserAuth(), user.getUsername(), user.getFullName(), user.getHomeCountry(), user.getMale(), user.getAge());
		suser.save();
		return suser.getEncodedUsername();
	}
	
	public CUser checkUser (String userAuth) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        String encodedKey = null;
		try {
        	encodedKey = KeyFactory.keyToString((KeyFactory.createKey(SUser.class.getSimpleName(), userAuth)));
        	try {
        		SUser user = pm.getObjectById(SUser.class, encodedKey);
        		return user.getCUser();
        	} catch (Exception e) {
           		return null;
        	}
        } finally {
            pm.close();
        }
	}
}
