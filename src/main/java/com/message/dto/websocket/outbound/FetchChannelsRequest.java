package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;

public class FetchChannelsRequest extends BaseRequest {

	public FetchChannelsRequest() {
		super(MessageType.FETCH_CHANNELS_REQUEST);
	}
}
