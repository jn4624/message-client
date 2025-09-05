package com.message.handler;

import com.message.dto.Message;
import com.message.service.TerminalService;
import com.message.util.JsonUtil;

import jakarta.websocket.Session;

public class WebSocketSender {

	private final TerminalService terminalService;

	public WebSocketSender(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public void sendMessage(Session session, Message message) {
		if (session != null && session.isOpen()) {
			JsonUtil.toJson(message)
				.ifPresent(
					msg -> {
						try {
							session.getBasicRemote().sendText(msg);
						} catch (Exception e) {
							terminalService.printSystemMessage(
								String.format("%s send failed. error: %s", msg, e.getMessage()));
						}
					});
		}
	}
}
