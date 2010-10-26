package bzb.gwt.planner.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;

public class ActivityIndicator extends DialogBox {

	public ActivityIndicator () {
		setText("Loading ...");
		setWidget(new Image("activityIndicator.gif"));
	}
	
}
