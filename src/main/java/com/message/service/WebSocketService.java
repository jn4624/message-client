package com.message.service;

import java.net.URI;

import org.glassfish.tyrus.client.ClientManager;

import com.message.dto.Message;
import com.message.handler.WebSocketMessageHandler;
import com.message.handler.WebSocketSender;
import com.message.handler.WebSocketSessionHandler;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

public class WebSocketService {

	private final TerminalService terminalService;
	private final WebSocketSender webSocketSender;
	private final String webSocketUrl;
	private WebSocketMessageHandler webSocketMessageHandler;
	private Session session;

	public WebSocketService(TerminalService terminalService, WebSocketSender webSocketSender, String url,
		String endpoint) {
		this.terminalService = terminalService;
		this.webSocketSender = webSocketSender;
		this.webSocketUrl = "ws://" + url + endpoint;
	}

	public void setWebSocketMessageHandler(WebSocketMessageHandler webSocketMessageHandler) {
		this.webSocketMessageHandler = webSocketMessageHandler;
	}

	public boolean createSession() {
		ClientManager clientManager = ClientManager.createClient();

		try {
			session = clientManager.connectToServer(
				new WebSocketSessionHandler(terminalService), new URI(webSocketUrl));
			session.addMessageHandler(webSocketMessageHandler);
			return true;
		} catch (Exception e) {
			terminalService.printSystemMessage(
				String.format("Failed to connect to [%s] error: %s", webSocketUrl, e.getMessage()));
			return false;
		}
	}

	public void closeSession() {
		try {
			if (session != null) {
				/*
				  - SpringBoot에서 제공하는 WebSocket은 바로 session.close()를 해도 문제가 없다.
				    닫혀 있는 session을 close해도 site effect가 없다.
				  - 하지만 tyrus가 제공하는 Session 객체는 닫혀 있는 session을 close하면 예외를 던진다.
				    따라서 열려 있는지 확인하고 close 처리해야 한다.
				 */
				if (session.isOpen()) {
					session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "NORMAL_CLOSURE"));
				}
				session = null;
			}
		} catch (Exception e) {
			terminalService.printSystemMessage(
				String.format("Failed to close. error: %s", e.getMessage()));
		}
	}

	/*
	  - WebSocketSender 클래스에 sendMessage가 구현되어 있는데 여기서 또 구현하는 이유는
	    WebSocketSender의 sendMessage를 다른 객체가 직접 사용하는 일을 만들지 않고
	    Service를 통해서만 sendMessage를 사용할 수 있도록하기 위함이다.
	 */
	public void sendMessage(Message message) {
		if (session != null && session.isOpen()) {
			webSocketSender.sendMessage(session, message);
		} else {
			terminalService.printSystemMessage("Failed to send message. Session is not open.");

		}
	}
}
