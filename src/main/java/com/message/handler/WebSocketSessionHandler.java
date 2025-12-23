package com.message.handler;

import com.message.service.TerminalService;
import com.message.service.UserService;
import com.message.service.WebSocketService;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

public class WebSocketSessionHandler extends Endpoint {

	private final UserService userService;
	private final TerminalService terminalService;
	private final WebSocketService webSocketService;

	// Spring 환경이 아니어도 생성자를 통해 의존성 주입 가능
	public WebSocketSessionHandler(
		UserService userService,
		TerminalService terminalService,
		WebSocketService webSocketService
	) {
		this.userService = userService;
		this.terminalService = terminalService;
		this.webSocketService = webSocketService;
	}

	@Override
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		terminalService.printSystemMessage("WebSocket connected.");
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		userService.logout();
		webSocketService.closeSession();
		terminalService.printSystemMessage("WebSocket closed. CloseReason: " + closeReason);
	}

	@Override
	public void onError(Session session, Throwable thr) {
		terminalService.printSystemMessage("WebSocket Error: " + thr.getMessage());
	}
}
