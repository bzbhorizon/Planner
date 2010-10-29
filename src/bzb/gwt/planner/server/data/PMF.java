package bzb.gwt.planner.server.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
    
    /*
     * PersistenceManager pm = PMF.get().getPersistenceManager();
     * try {
     * // ... do stuff with pm ...
     * } finally {
     * 	pm.close();
     * }
     */
}
