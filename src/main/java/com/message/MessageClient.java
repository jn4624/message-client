package com.message;

import java.io.IOException;

import com.message.dto.websocket.outbound.WriteMessageRequest;
import com.message.handler.CommandHandler;
import com.message.handler.InboundMessageHandler;
import com.message.handler.WebSocketMessageHandler;
import com.message.handler.WebSocketSender;
import com.message.service.RestApiService;
import com.message.service.TerminalService;
import com.message.service.WebSocketService;

public class MessageClient {
	public static void main(String[] args) {
		final String BASE_URL = "localhost:8080";
		final String WEBSOCKET_ENDPOINT = "/ws/v1/message";

		TerminalService terminalService;

		try {
			terminalService = TerminalService.create();
		} catch (IOException e) {
			System.err.print("Failed to run MessageClient");
			return;
		}

		InboundMessageHandler inboundMessageHandler = new InboundMessageHandler(terminalService);
		RestApiService restApiService = new RestApiService(terminalService, BASE_URL);
		WebSocketSender webSocketSender = new WebSocketSender(terminalService);
		WebSocketService webSocketService =
			new WebSocketService(terminalService, webSocketSender, BASE_URL, WEBSOCKET_ENDPOINT);
		webSocketService.setWebSocketMessageHandler(new WebSocketMessageHandler(inboundMessageHandler));
		CommandHandler commandHandler = new CommandHandler(restApiService, webSocketService, terminalService);

		while (true) {
			String input = terminalService.readLine("Enter message: ");

			if (!input.isEmpty() && input.charAt(0) == '/') {
				String[] parts = input.split(" ", 2);
				String command = parts[0].substring(1);
				String argument = parts.length > 1 ? parts[1] : "";

				if (!commandHandler.process(command, argument)) {
					break;
				}
			} else if (!input.isEmpty()) {
				terminalService.printMessage("<me>", input);
				webSocketService.sendMessage(new WriteMessageRequest("test client", input));
			}
		}
	}
}
