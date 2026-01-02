package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.ChannelId;

public class QuitRequest extends BaseRequest {

	private final ChannelId channelId;

	public QuitRequest(ChannelId channelId) {
		super(MessageType.QUIT_REQUEST);
		this.channelId = channelId;
	}

	public ChannelId getChannelId() {
		return channelId;
	}
}
