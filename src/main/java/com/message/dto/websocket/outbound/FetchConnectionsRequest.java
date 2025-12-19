package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;
import com.message.constant.UserConnectionStatus;

public class FetchConnectionsRequest extends BaseRequest {

	private final UserConnectionStatus status;

	public FetchConnectionsRequest(UserConnectionStatus status) {
		super(MessageType.FETCH_CONNECTIONS_REQUEST);
		this.status = status;
	}

	public UserConnectionStatus getStatus() {
		return status;
	}
}
