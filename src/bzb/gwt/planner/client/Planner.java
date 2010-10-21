package bzb.gwt.planner.client;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Planner implements EntryPoint {

	public void onModuleLoad() {
		Calendar calendar = new Calendar();
		calendar.setDate(new Date()); //calendar date, not required
		calendar.setDays(7); //number of days displayed at a time, not required
		RootPanel.get("bodyContainer").add(calendar);
	}
}
