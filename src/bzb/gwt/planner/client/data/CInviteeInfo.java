package bzb.gwt.planner.client.data;

import java.io.Serializable;

public class CInviteeInfo implements Serializable {
	
	private static final long serialVersionUID = -1853888326643014991L;
	private CInvitation invitation;
	private CUser invitee;
	
	public CInviteeInfo () {
		
	}
	
	public CInviteeInfo (CInvitation invitation, CUser invitee) {
		setInvitation(invitation);
		setInvitee(invitee);
	}
	
	public void setInvitation(CInvitation invitation) {
		this.invitation = invitation;
	}
	public CInvitation getInvitation() {
		return invitation;
	}
	public void setInvitee(CUser invitee) {
		this.invitee = invitee;
	}

	public CUser getInvitee() {
		return invitee;
	}

}
