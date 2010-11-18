package bzb.gwt.planner.client.data;

import java.io.Serializable;

public class CInvitationInfo implements Serializable {
	
	private static final long serialVersionUID = 3673354178176130327L;
	private CInvitation invitation;
	private CTrip trip;
	private CUser inviter;
	
	public CInvitationInfo () {
		
	}
	
	public CInvitationInfo (CInvitation invitation, CTrip trip, CUser inviter) {
		setInvitation(invitation);
		setTrip(trip);
		setInviter(inviter);
	}
	
	public void setInvitation(CInvitation invitation) {
		this.invitation = invitation;
	}
	public CInvitation getInvitation() {
		return invitation;
	}
	public void setTrip(CTrip trip) {
		this.trip = trip;
	}
	public CTrip getTrip() {
		return trip;
	}
	public void setInviter(CUser inviter) {
		this.inviter = inviter;
	}
	public CUser getInviter() {
		return inviter;
	}

}
