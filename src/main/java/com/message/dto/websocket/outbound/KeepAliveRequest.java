package com.message.dto.websocket.outbound;

import com.message.constants.MessageType;

public class KeepAliveRequest extends BaseRequest {

	public KeepAliveRequest() {
		super(MessageType.KEEP_ALIVE);
	}
}
