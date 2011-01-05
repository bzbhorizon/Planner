package bzb.gwt.planner.client.data;

import java.io.Serializable;

public abstract class CSegment implements Serializable {

	private static final long serialVersionUID = -8787984339619332L;
	
	private int x = 0;
	private int y = 0;
	
	public CSegment () {}

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
