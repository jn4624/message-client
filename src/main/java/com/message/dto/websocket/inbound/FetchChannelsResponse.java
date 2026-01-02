package com.message.dto.websocket.inbound;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.message.constant.MessageType;
import com.message.dto.domain.Channel;

public class FetchChannelsResponse extends BaseMessage {

	private final List<Channel> channels;

	@JsonCreator
	public FetchChannelsResponse(@JsonProperty("channels") List<Channel> channels) {
		super(MessageType.FETCH_CHANNELS_RESPONSE);
		this.channels = channels;
	}

	public List<Channel> getChannels() {
		return channels;
	}
}
