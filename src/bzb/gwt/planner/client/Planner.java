package bzb.gwt.planner.client;

import bzb.gwt.planner.client.panels.HeadNavPanel;
import bzb.gwt.planner.client.panels.LoginPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class Planner implements EntryPoint {
	
	public static enum State {WELCOME, TRIPS, LOCATIONS, PLANNING};
	private static State state = State.WELCOME;
	
	private static CUser user;

	public void onModuleLoad() {
		/*Calendar calendar = new Calendar();
		calendar.setDate(new Date()); //calendar date, not required
		calendar.setDays(7); //number of days displayed at a time, not required*/
		updateContent();
	}
	
	public static void updateContent (State newState) {
		setState(newState);
		updateContent();
	}
	
	public static void updateContent () {
		RootPanel.get("bodyContainer").clear();
		RootPanel.get("bodyContainer").add(getBodyPanel());
		
		RootPanel.get("headContainer").clear();
		RootPanel.get("headContainer").add(new HeadNavPanel());
	}
	
	private static Panel getBodyPanel() {
		if (state == State.WELCOME) {
			return new LoginPanel();
		} else {
			return null;
		}
	}

	public static void setUser(CUser user) {
		Planner.user = user;
	}

	public static CUser getUser() {
		return user;
	}
	
	public static void setState(State state) {
		Planner.state = state;
	}

	public static State getState() {
		return state;
	}
}
