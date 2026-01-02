package com.message.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.message.constant.MessageType;

// 정의된 타입을 가지고 서브 클래스인 json을 객체로 변환할 때 맞는 객체로 변환 가능
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = FetchUserInviteCodeResponse.class, name = MessageType.FETCH_USER_INVITE_CODE_RESPONSE),
	@JsonSubTypes.Type(value = FetchConnectionsResponse.class, name = MessageType.FETCH_CONNECTIONS_RESPONSE),
	@JsonSubTypes.Type(value = InviteResponse.class, name = MessageType.INVITE_RESPONSE),
	@JsonSubTypes.Type(value = AcceptResponse.class, name = MessageType.ACCEPT_RESPONSE),
	@JsonSubTypes.Type(value = RejectResponse.class, name = MessageType.REJECT_RESPONSE),
	@JsonSubTypes.Type(value = DisconnectResponse.class, name = MessageType.DISCONNECT_RESPONSE),
	@JsonSubTypes.Type(value = FetchChannelInviteCodeResponse.class, name = MessageType.FETCH_CHANNEL_INVITE_CODE_RESPONSE),
	@JsonSubTypes.Type(value = FetchChannelsResponse.class, name = MessageType.FETCH_CHANNELS_RESPONSE),
	@JsonSubTypes.Type(value = CreateResponse.class, name = MessageType.CREATE_RESPONSE),
	@JsonSubTypes.Type(value = EnterResponse.class, name = MessageType.ENTER_RESPONSE),
	@JsonSubTypes.Type(value = JoinResponse.class, name = MessageType.JOIN_RESPONSE),
	@JsonSubTypes.Type(value = LeaveResponse.class, name = MessageType.LEAVE_RESPONSE),
	@JsonSubTypes.Type(value = QuitResponse.class, name = MessageType.QUIT_RESPONSE),

	@JsonSubTypes.Type(value = InviteNotification.class, name = MessageType.ASK_INVITE),
	@JsonSubTypes.Type(value = JoinNotification.class, name = MessageType.NOTIFY_JOIN),
	@JsonSubTypes.Type(value = MessageNotification.class, name = MessageType.NOTIFY_MESSAGE),
	@JsonSubTypes.Type(value = AcceptNotification.class, name = MessageType.NOTIFY_ACCEPT),
	@JsonSubTypes.Type(value = ErrorResponse.class, name = MessageType.ERROR)
})
public abstract class BaseMessage {

	private final String type;

	public BaseMessage(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
