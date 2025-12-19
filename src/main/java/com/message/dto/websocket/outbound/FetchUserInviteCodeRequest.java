package com.message.dto.websocket.outbound;

import com.message.constant.MessageType;

public class FetchUserInviteCodeRequest extends BaseRequest {

	public FetchUserInviteCodeRequest() {
		super(MessageType.FETCH_USER_INVITE_CODE_REQUEST);
	}
}
