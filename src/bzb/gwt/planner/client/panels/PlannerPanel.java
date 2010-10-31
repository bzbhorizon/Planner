package bzb.gwt.planner.client.panels;

import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.Planner.State;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public abstract class PlannerPanel extends FlowPanel implements IPlannerPanel {
	
	protected HorizontalPanel getHQNav () {
		HorizontalPanel hp = new HorizontalPanel();
		if (Planner.getUser() != null) {
			hp.add(getNavButton(State.HQ, Planner.getUser().getUsername()));
		} else {
			hp.add(getNavButton(State.WELCOME, "Welcome"));
		}
		return hp;
	}
	
	protected Button getNavButton (final State state, String text) {
		Button button = new Button(text);
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Planner.updateContent(state);
			}
		});
		return button;
	}

}
