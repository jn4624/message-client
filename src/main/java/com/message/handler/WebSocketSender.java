package com.message.handler;

import com.message.dto.websocket.outbound.WriteMessage;
import com.message.service.TerminalService;
import com.message.util.JsonUtil;

import jakarta.websocket.Session;

public class WebSocketSender {

	private final TerminalService terminalService;

	public WebSocketSender(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public void sendMessage(Session session, WriteMessage message) {
		if (session != null && session.isOpen()) {
			JsonUtil.toJson(message)
				.ifPresent(
					payload ->
						// 동기 방식
						// session.getBasicRemote().sendText(payload);

						// 비동기 방식
						session.getAsyncRemote().sendText(payload, result -> {
							if (!result.isOK()) {
								terminalService.printSystemMessage(
									"'%s' send failed. cause: %s".formatted(payload, result.getException()));
							}
						}));

		}
	}
}
