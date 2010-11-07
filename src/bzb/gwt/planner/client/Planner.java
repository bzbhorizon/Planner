package bzb.gwt.planner.client;

import bzb.gwt.planner.client.data.CTrip;
import bzb.gwt.planner.client.data.CUser;
import bzb.gwt.planner.client.panels.HQPanel;
import bzb.gwt.planner.client.panels.LoginPanel;
import bzb.gwt.planner.client.panels.PlannerPanel;
import bzb.gwt.planner.client.panels.PlanningPanel;
import bzb.gwt.planner.client.panels.TripsPanel;
import bzb.gwt.planner.shared.data.Paths;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Planner implements EntryPoint {
	
	public static enum State {WELCOME, HQ, TRIPS, TRIP, PLANNING};
	private static State state = State.WELCOME;
	
	public static final SignInServiceAsync signinService = GWT.create(SignInService.class);
	public static final DatastoreServiceAsync datastoreService = GWT.create(DatastoreService.class);
	
	private static CUser user;
	private static CTrip trip;
	
	private static FlowPanel body;
	private static HorizontalPanel nav;
	
	private static PlannerPanel loginPanel;
	private static PlannerPanel hqPanel;
	private static PlannerPanel tripsPanel;
	private static PlannerPanel planningPanel;
	
	private static Button logout;
	
	private static ActivityIndicator ai;

	public void onModuleLoad() {
		/*Calendar calendar = new Calendar();
		calendar.setDate(new Date()); //calendar date, not required
		calendar.setDays(7); //number of days displayed at a time, not required*/
		
		body = new FlowPanel();
		RootPanel.get("bodyContainer").add(body);
		
		setNav(new HorizontalPanel());
		RootPanel.get("headContainer").add(getNav());
		
		logout = new Button("Logout");
		hideLogout();
		logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign("http://m.facebook.com/logout.php?confirm=1&next=" + Paths.RETURN_TO_PATH);
			}
		});
		RootPanel.get("footContainer").add(logout);
		
		ai = new ActivityIndicator();
		updateContent();
	}
	
	public static void showLogout () {
		logout.setVisible(true);
		logout.setEnabled(true);
	}
	
	public static void hideLogout () {
		logout.setVisible(false);
		logout.setEnabled(false);
	}
	
	public static void updateContent (State newState) {
		setState(newState);
		updateContent();
	}
	
	public static void updateContent () {
		body.clear();
		body.add(getBodyPanel());
		
		getNav().clear();
		getNav().add(((PlannerPanel)body.getWidget(0)).getNav());
		
		if (state == State.TRIP) {
			getNav().add(new Button(getTrip().getName()));
		}
	}
	
	private static PlannerPanel getBodyPanel() {
		if (state == State.WELCOME) {
			if (loginPanel == null) {
				loginPanel = new LoginPanel();
			}
			return loginPanel;
		} else {
			showLogout();
			Window.addWindowClosingHandler(new ClosingHandler() {
				public void onWindowClosing(ClosingEvent event) {
					event.setMessage("Don't leave");
				}
			});
			
			if (state == State.HQ) {
				if (hqPanel == null) {
					hqPanel = new HQPanel();
				}
				return hqPanel;
			} else if (state == State.TRIPS) {
				if (tripsPanel == null) {
					tripsPanel = new TripsPanel();
				}
				((TripsPanel)tripsPanel).listTrips();
				return tripsPanel;
			} else if (state == State.TRIP) {
				if (tripsPanel == null) {
					tripsPanel = new TripsPanel();
				}
				return tripsPanel;
			} else if (state == State.PLANNING) {
				if (planningPanel == null) {
					planningPanel = new PlanningPanel(getTrip());
				}
				return planningPanel;
			} else {
				return null;
			}
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
	
	public static void showActivityIndicator () {
		ai.center();
	}
	
	public static void hideActivityIndicator () {
		ai.hide();
	}

	public static void setNav(HorizontalPanel nav) {
		Planner.nav = nav;
	}

	public static HorizontalPanel getNav() {
		return nav;
	}

	public static void setTrip(CTrip trip) {
		Planner.trip = trip;
	}

	public static CTrip getTrip() {
		return trip;
	}

}
