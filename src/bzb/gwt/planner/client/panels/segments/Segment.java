package bzb.gwt.planner.client.panels.segments;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class Segment extends FocusPanel {
	private final PickupDragController dragController;
	private DropController dropController = new SegmentSetWidgetDropContoller(
			this);

	public Segment(PickupDragController dragController) {
		this.dragController = dragController;
		VerticalPanel vp = new VerticalPanel();
		setWidget(vp);
		setStyleName("segment");
		setWidth("25%");
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		dragController.registerDropController(dropController);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		dragController.unregisterDropController(dropController);
	}
}
