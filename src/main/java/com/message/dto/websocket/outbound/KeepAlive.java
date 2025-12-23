package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;

public class KeepAlive extends BaseRequest {

	public KeepAlive() {
		super(MessageType.KEEP_ALIVE);
	}
}
