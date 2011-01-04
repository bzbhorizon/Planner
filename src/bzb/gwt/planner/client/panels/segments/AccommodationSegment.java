package bzb.gwt.planner.client.panels.segments;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

public class AccommodationSegment extends Segment {

	public AccommodationSegment(PickupDragController dragController) {
		super(dragController);
		addStyleName("accomSegment");
	}

}
