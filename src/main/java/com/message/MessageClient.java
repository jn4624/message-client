package com.message;

import java.io.IOException;

import com.message.dto.websocket.outbound.WriteMessage;
import com.message.handler.CommandHandler;
import com.message.handler.InboundMessageHandler;
import com.message.handler.WebSocketMessageHandler;
import com.message.handler.WebSocketSender;
import com.message.service.RestApiService;
import com.message.service.TerminalService;
import com.message.service.UserService;
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

		UserService userService = new UserService();
		InboundMessageHandler inboundMessageHandler = new InboundMessageHandler(userService, terminalService);
		RestApiService restApiService = new RestApiService(terminalService, BASE_URL);
		WebSocketSender webSocketSender = new WebSocketSender(terminalService);
		WebSocketService webSocketService =
			new WebSocketService(userService, terminalService, webSocketSender, BASE_URL, WEBSOCKET_ENDPOINT);
		webSocketService.setWebSocketMessageHandler(new WebSocketMessageHandler(inboundMessageHandler));
		CommandHandler commandHandler = new CommandHandler(userService, restApiService, webSocketService,
			terminalService);

		terminalService.printSystemMessage("'/help' Help for commands. ex: /help");

		while (true) {
			String input = terminalService.readLine("Enter message: ");

			if (!input.isEmpty() && input.charAt(0) == '/') {
				String[] parts = input.split(" ", 2);
				String command = parts[0].substring(1);
				String argument = parts.length > 1 ? parts[1] : "";

				if (!commandHandler.process(command, argument)) {
					break;
				}
			} else if (!input.isEmpty() && userService.isInChannel()) {
				terminalService.printMessage("<me>", input);
				webSocketService.sendMessage(
					new WriteMessage(userService.getChannelId(), input));
			}
		}
	}
}
