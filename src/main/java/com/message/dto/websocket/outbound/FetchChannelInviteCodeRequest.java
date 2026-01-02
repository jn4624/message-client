package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.dto.domain.ChannelId;

public class FetchChannelInviteCodeRequest extends BaseRequest {

	private final ChannelId channelId;

	public FetchChannelInviteCodeRequest(ChannelId channelId) {
		super(MessageType.FETCH_CHANNEL_INVITE_CODE_REQUEST);
		this.channelId = channelId;
	}

	public ChannelId getChannelId() {
		return channelId;
	}
}
