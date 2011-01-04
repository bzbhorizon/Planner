package bzb.gwt.planner.client.panels.segments;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SegmentSetWidgetDropContoller extends SimpleDropController {
	private static void makeLastChild(Widget child) {
		Widget parent = child.getParent();
		if (parent instanceof AbsolutePanel) {
			AbsolutePanel p = (AbsolutePanel) child.getParent();
			p.add(child, p.getWidgetLeft(child), p.getWidgetTop(child));
		}
	}

	private final SimplePanel dropTarget;

	public SegmentSetWidgetDropContoller(SimplePanel dropTarget) {
		super(dropTarget);
		this.dropTarget = dropTarget;
	}

	@Override
	public void onDrop(DragContext context) {
		makeLastChild(dropTarget);
		dropTarget.setWidget(context.draggable);
		super.onDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
	}

	@Override
	public void onLeave(DragContext context) {
		// dropTarget.removeStyleName(CSS_DEMO_MATRYOSHKA_EXAMPLE_DROP_TARGET_ENGAGE);
		super.onLeave(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
	}
}
