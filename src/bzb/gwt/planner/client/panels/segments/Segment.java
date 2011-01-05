package bzb.gwt.planner.client.panels.segments;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class Segment extends FocusPanel {
	private final PickupDragController dragController;
	private DropController dropController = new SegmentSetWidgetDropContoller(
			this);
	
	private int x = 0;
	private int y = 0;

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

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
}
