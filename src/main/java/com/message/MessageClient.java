package com.message;

import java.io.IOException;

import com.message.dto.Message;
import com.message.handler.WebSocketMessageHandler;
import com.message.handler.WebSocketSender;
import com.message.service.TerminalService;
import com.message.service.WebSocketService;

public class MessageClient {
	public static void main(String[] args) {
		final String WEBSOCKET_BASE_URL = "localhost:8080";
		final String WEBSOCKET_ENDPOINT = "/ws/v1/message";

		TerminalService terminalService;

		try {
			terminalService = TerminalService.create();
		} catch (IOException e) {
			System.err.print("Failed to run MessageClient");
			return;
		}

		WebSocketSender webSocketSender = new WebSocketSender(terminalService);
		WebSocketService webSocketService =
			new WebSocketService(terminalService, webSocketSender, WEBSOCKET_BASE_URL, WEBSOCKET_ENDPOINT);
		webSocketService.setWebSocketMessageHandler(new WebSocketMessageHandler(terminalService));

		while (true) {
			String input = terminalService.readLine("Enter message: ");

			if (!input.isEmpty() && input.charAt(0) == '/') {
				String command = input.substring(1);

				/*
				  - 일반적인 형식의 switch문을 사용하게 되면
				    case문에서 break를 사용해도 switch문을 빠져나오는거지 while문을 빠져나가는게 아니다.
				    따라서 switch 표현식으로 Java 14에서 추가된 기능을 사용하는 것으로 한다.
				  - switch 표현식에서는 case문에서 yield 키워드를 사용해 값을 반환할 수 있다.
				  - switch 표현식에서는 case문에서 break 키워드를 사용하지 않는다.
				 */
				boolean exit = switch (command) {
					case "exit" -> {
						webSocketService.closeSession();
						yield true;
					}
					case "clear" -> {
						terminalService.clearTerminal();
						yield false;
					}
					case "connect" -> {
						webSocketService.createSession();
						yield false;
					}
					default -> false;
				};

				if (exit) {
					break;
				}
			} else if (!input.isEmpty()) {
				terminalService.printMessage("<me>", input);
				webSocketService.sendMessage(new Message("test client", input));
			}
		}
	}
}
