package bzb.gwt.planner.client;

import bzb.gwt.planner.client.panels.HeadNavPanel;
import bzb.gwt.planner.client.panels.LoginPanel;
import bzb.gwt.planner.shared.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class Planner implements EntryPoint {
	
	private static enum STATE {WELCOME, LOCATIONS, PLANNING};
	private static STATE state = STATE.WELCOME;
	
	private static User user;

	public void onModuleLoad() {
		/*Calendar calendar = new Calendar();
		calendar.setDate(new Date()); //calendar date, not required
		calendar.setDays(7); //number of days displayed at a time, not required*/
		RootPanel.get("bodyContainer").add(getBodyPanel());
		RootPanel.get("headContainer").add(new HeadNavPanel());
	}
	
	private Panel getBodyPanel() {
		if (state == STATE.WELCOME) {
			return new LoginPanel(this);
		} else {
			return null;
		}
	}

	public static void setUser(User user) {
		Planner.user = user;
	}

	public static User getUser() {
		return user;
	}
}
