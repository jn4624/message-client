package com.message.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.message.constant.MessageType;
import com.message.dto.domain.ChannelId;

public class QuitResponse extends BaseMessage {

	private final ChannelId channelId;

	@JsonCreator
	public QuitResponse(@JsonProperty("channelId") ChannelId channelId) {
		super(MessageType.QUIT_RESPONSE);
		this.channelId = channelId;
	}

	public ChannelId getChannelId() {
		return channelId;
	}
}
