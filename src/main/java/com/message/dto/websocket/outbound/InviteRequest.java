package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.InviteCode;

public class InviteRequest extends BaseRequest {

	private final InviteCode userInviteCode;

	public InviteRequest(InviteCode userInviteCode) {
		super(MessageType.INVITE_REQUEST);
		this.userInviteCode = userInviteCode;
	}

	public InviteCode getUserInviteCode() {
		return userInviteCode;
	}
}
