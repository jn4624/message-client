package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.ChannelId;

public class WriteMessage extends BaseRequest {

	private final ChannelId channelId;
	private final String content;

	public WriteMessage(ChannelId channelId, String content) {
		super(MessageType.WRITE_MESSAGE);
		this.channelId = channelId;
		this.content = content;
	}

	public ChannelId getChannelId() {
		return channelId;
	}

	public String getContent() {
		return content;
	}
}
