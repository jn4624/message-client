package com.message.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.message.constants.MessageType;

public class MessageRequest extends BaseRequest {

	private final String username;
	private final String content;

	public MessageRequest(
		@JsonProperty("username") String username,
		@JsonProperty("content") String content
	) {
		super(MessageType.MESSAGE);
		this.username = username;
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public String getContent() {
		return content;
	}
}
