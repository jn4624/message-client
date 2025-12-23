package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.ChannelId;

public class EnterRequest extends BaseRequest {

	private final ChannelId channelId;

	public EnterRequest(ChannelId channelId) {
		super(MessageType.ENTER_REQUEST);
		this.channelId = channelId;
	}

	public ChannelId getChannelId() {
		return channelId;
	}
}
