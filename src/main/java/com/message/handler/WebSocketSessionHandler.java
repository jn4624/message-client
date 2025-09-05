package com.message.handler;

import com.message.service.TerminalService;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

public class WebSocketSessionHandler extends Endpoint {

	private final TerminalService terminalService;

	// Spring 환경이 아니어도 생성자를 통해 의존성 주입 가능
	public WebSocketSessionHandler(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	@Override
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		terminalService.printSystemMessage("WebSocket connected.");
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		terminalService.printSystemMessage("WebSocket closed. CloseReason: " + closeReason);
	}

	@Override
	public void onError(Session session, Throwable thr) {
		terminalService.printSystemMessage("WebSocket Error: " + thr.getMessage());
	}
}
