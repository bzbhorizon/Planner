package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Utility;
import bzb.gwt.planner.client.data.CUser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserProfileDialog extends DialogBox {

	public UserProfileDialog (String username) {
		final DockPanel dp = new DockPanel();
		
		Planner.showActivityIndicator();
		Planner.datastoreService.checkUserByUsername(username, new AsyncCallback<CUser>() {
			public void onSuccess(CUser user) {
				setText(user.getFullName() + "'s public profile");
				
				VerticalPanel info = new VerticalPanel();
				info.add(new HTML(user.getFullName()));
				info.add(new HTML(user.getHomeCountry()));
				info.add(new HTML(String.valueOf(user.getAge())));
				info.add(new HTML(String.valueOf(user.getMale())));
				info.add(new HTML(Utility.formatDateTime(user.getCreationTime())));
				
				dp.add(info, DockPanel.CENTER);
				
				center();
				
				Planner.hideActivityIndicator();
			}
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Planner.hideActivityIndicator();
			}
		});
		
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		dp.add(close, DockPanel.SOUTH);
		
		setWidget(dp);
		
		center();
	}
	
}
