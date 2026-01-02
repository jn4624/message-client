package com.message.handler;

import com.message.dto.websocket.inbound.AcceptNotification;
import com.message.dto.websocket.inbound.AcceptResponse;
import com.message.dto.websocket.inbound.BaseMessage;
import com.message.dto.websocket.inbound.CreateResponse;
import com.message.dto.websocket.inbound.DisconnectResponse;
import com.message.dto.websocket.inbound.EnterResponse;
import com.message.dto.websocket.inbound.ErrorResponse;
import com.message.dto.websocket.inbound.FetchChannelInviteCodeResponse;
import com.message.dto.websocket.inbound.FetchChannelsResponse;
import com.message.dto.websocket.inbound.FetchConnectionsResponse;
import com.message.dto.websocket.inbound.FetchUserInviteCodeResponse;
import com.message.dto.websocket.inbound.InviteNotification;
import com.message.dto.websocket.inbound.InviteResponse;
import com.message.dto.websocket.inbound.JoinNotification;
import com.message.dto.websocket.inbound.JoinResponse;
import com.message.dto.websocket.inbound.LeaveResponse;
import com.message.dto.websocket.inbound.MessageNotification;
import com.message.dto.websocket.inbound.QuitResponse;
import com.message.dto.websocket.inbound.RejectResponse;
import com.message.service.TerminalService;
import com.message.service.UserService;
import com.message.util.JsonUtil;

public class InboundMessageHandler {

	private final UserService userService;
	private final TerminalService terminalService;

	public InboundMessageHandler(UserService userService, TerminalService terminalService) {
		this.userService = userService;
		this.terminalService = terminalService;
	}

	public void handle(String payload) {
		JsonUtil.fromJson(payload, BaseMessage.class)
			.ifPresent(message -> {
				if (message instanceof MessageNotification messageNotification) {
					message(messageNotification);
				} else if (message instanceof FetchUserInviteCodeResponse fetchUserInviteCodeResponse) {
					fetchUserInviteCode(fetchUserInviteCodeResponse);
				} else if (message instanceof FetchChannelInviteCodeResponse fetchChannelInviteCodeResponse) {
					fetchChannelInviteCode(fetchChannelInviteCodeResponse);
				} else if (message instanceof InviteResponse inviteResponse) {
					invite(inviteResponse);
				} else if (message instanceof InviteNotification inviteNotification) {
					askInvite(inviteNotification);
				} else if (message instanceof AcceptResponse acceptResponse) {
					accept(acceptResponse);
				} else if (message instanceof AcceptNotification acceptNotification) {
					acceptNotification(acceptNotification);
				} else if (message instanceof RejectResponse rejectResponse) {
					reject(rejectResponse);
				} else if (message instanceof DisconnectResponse disconnectResponse) {
					disconnect(disconnectResponse);
				} else if (message instanceof FetchConnectionsResponse fetchConnectionsResponse) {
					connections(fetchConnectionsResponse);
				} else if (message instanceof FetchChannelsResponse fetchChannelsResponse) {
					fetchChannels(fetchChannelsResponse);
				} else if (message instanceof CreateResponse createResponse) {
					create(createResponse);
				} else if (message instanceof JoinNotification joinNotification) {
					joinNotification(joinNotification);
				} else if (message instanceof JoinResponse joinResponse) {
					join(joinResponse);
				} else if (message instanceof EnterResponse enterResponse) {
					enter(enterResponse);
				} else if (message instanceof LeaveResponse leaveResponse) {
					leave(leaveResponse);
				} else if (message instanceof QuitResponse quitResponse) {
					quit(quitResponse);
				} else if (message instanceof ErrorResponse errorResponse) {
					error(errorResponse);
				}
			});
	}

	private void message(MessageNotification messageNotification) {
		terminalService.printMessage(messageNotification.getUsername(), messageNotification.getContent());
	}

	private void fetchUserInviteCode(FetchUserInviteCodeResponse fetchUserInviteCodeResponse) {
		terminalService.printSystemMessage("My inviteCode: %s".formatted(fetchUserInviteCodeResponse.getInviteCode()));
	}

	private void fetchChannelInviteCode(FetchChannelInviteCodeResponse fetchChannelInviteCodeResponse) {
		terminalService.printSystemMessage(
			"%s InviteCode: %s".formatted(
				fetchChannelInviteCodeResponse.getChannelId(), fetchChannelInviteCodeResponse.getInviteCode()));
	}

	private void invite(InviteResponse inviteResponse) {
		terminalService.printSystemMessage(
			"Invite %s result: %s".formatted(inviteResponse.getInviteCode(), inviteResponse.getStatus()));
	}

	private void askInvite(InviteNotification inviteNotification) {
		terminalService.printSystemMessage(
			"Do you accept %s's connection request?".formatted(inviteNotification.getUsername()));
	}

	private void accept(AcceptResponse acceptResponse) {
		terminalService.printSystemMessage("Connected %s.".formatted(acceptResponse.getUsername()));
	}

	private void acceptNotification(AcceptNotification acceptNotification) {
		terminalService.printSystemMessage("Connected %s.".formatted(acceptNotification.getUsername()));
	}

	private void reject(RejectResponse rejectResponse) {
		terminalService.printSystemMessage(
			"Reject %s result: %s.".formatted(rejectResponse.getUsername(), rejectResponse.getStatus()));
	}

	private void disconnect(DisconnectResponse disconnectResponse) {
		terminalService.printSystemMessage(
			"Disconnected %s result: %s.".formatted(disconnectResponse.getUsername(), disconnectResponse.getStatus()));
	}

	private void connections(FetchConnectionsResponse fetchConnectionsResponse) {
		fetchConnectionsResponse.getConnections().forEach(connection ->
			terminalService.printSystemMessage("%s : %s".formatted(connection.username(), connection.status())));
	}

	private void fetchChannels(FetchChannelsResponse fetchChannelsResponse) {
		fetchChannelsResponse.getChannels().forEach(channel ->
			terminalService.printSystemMessage(
				"%s : %s (%d)".formatted(channel.channelId(), channel.title(), channel.headCount())));
	}

	private void create(CreateResponse createResponse) {
		terminalService.printSystemMessage(
			"Created channel %s: %s".formatted(createResponse.getChannelId(), createResponse.getTitle()));
	}

	private void joinNotification(JoinNotification joinNotification) {
		terminalService.printSystemMessage(
			"Joined channel %s: %s".formatted(joinNotification.getChannelId(), joinNotification.getTitle()));
	}

	private void join(JoinResponse joinResponse) {
		terminalService.printSystemMessage(
			"Joined channel %s: %s".formatted(joinResponse.getChannelId(), joinResponse.getTitle()));
	}

	private void enter(EnterResponse enterResponse) {
		userService.moveToChannel(enterResponse.getChannelId());
		terminalService.printSystemMessage(
			"Enter channel %s: %s".formatted(enterResponse.getChannelId(), enterResponse.getTitle()));
	}

	private void leave(LeaveResponse leaveResponse) {
		terminalService.printSystemMessage("Leave channel %s".formatted(userService.getChannelId()));
		userService.moveToLobby();
	}

	private void quit(QuitResponse quitResponse) {
		terminalService.printSystemMessage("Quit channel %s".formatted(quitResponse.getChannelId()));
	}

	private void error(ErrorResponse errorResponse) {
		terminalService.printSystemMessage(
			"Error %s: %s".formatted(errorResponse.getMessageType(), errorResponse.getMessage()));
	}
}
