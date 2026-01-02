package com.message.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.message.constant.MessageType;

public class LeaveResponse extends BaseMessage {

	@JsonCreator
	public LeaveResponse() {
		super(MessageType.LEAVE_RESPONSE);
	}
}
