package com.message.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.message.constant.MessageType;
import com.message.constant.UserConnectionStatus;
import com.message.dto.domain.InviteCode;

public class InviteResponse extends BaseMessage {

	private final InviteCode inviteCode;
	private final UserConnectionStatus status;

	@JsonCreator
	public InviteResponse(
		@JsonProperty("inviteCode") InviteCode inviteCode,
		@JsonProperty("status") UserConnectionStatus status) {
		super(MessageType.INVITE_RESPONSE);
		this.inviteCode = inviteCode;
		this.status = status;
	}

	public InviteCode getInviteCode() {
		return inviteCode;
	}

	public UserConnectionStatus getStatus() {
		return status;
	}
}
