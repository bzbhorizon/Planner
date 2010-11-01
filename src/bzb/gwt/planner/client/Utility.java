package bzb.gwt.planner.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public abstract class Utility {
	
	public static String formatDateTime (long timestamp) {
		return DateTimeFormat.getMediumDateTimeFormat().format(new Date(timestamp));
	}

}
