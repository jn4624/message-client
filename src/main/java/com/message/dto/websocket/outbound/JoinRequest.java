package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.InviteCode;

public class JoinRequest extends BaseRequest {

	private final InviteCode inviteCode;

	public JoinRequest(InviteCode inviteCode) {
		super(MessageType.JOIN_REQUEST);
		this.inviteCode = inviteCode;
	}

	public InviteCode getInviteCode() {
		return inviteCode;
	}
}
