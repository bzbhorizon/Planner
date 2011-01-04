package bzb.gwt.planner.client.panels.segments;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

public class TravelSegment extends Segment {

	public TravelSegment(PickupDragController dragController) {
		super(dragController);
		addStyleName("travelSegment");
	}

}
