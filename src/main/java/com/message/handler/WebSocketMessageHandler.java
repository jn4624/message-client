package com.message.handler;

import com.message.dto.Message;
import com.message.service.TerminalService;
import com.message.util.JsonUtil;

import jakarta.websocket.MessageHandler;

public class WebSocketMessageHandler implements MessageHandler.Whole<String> {

	private final TerminalService terminalService;

	public WebSocketMessageHandler(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	@Override
	public void onMessage(String payload) {
		JsonUtil.fromJson(payload, Message.class)
			.ifPresent(mesage -> terminalService.printMessage(mesage.username(), mesage.content()));
	}
}
