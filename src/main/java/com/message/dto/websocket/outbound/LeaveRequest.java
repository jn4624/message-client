package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;

public class LeaveRequest extends BaseRequest {

	public LeaveRequest() {
		super(MessageType.LEAVE_REQUEST);
	}
}
