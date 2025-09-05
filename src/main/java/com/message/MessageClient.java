package com.message;

import java.io.IOException;

import com.message.service.TerminalService;

public class MessageClient {
	public static void main(String[] args) {
		TerminalService terminalService;

		try {
			terminalService = TerminalService.create();
		} catch (IOException e) {
			System.err.print("Failed to run MessageClient");
			return;
		}

		while (true) {
			String input = terminalService.readLine("Enter message: ");

			if (!input.isEmpty() && input.charAt(0) == '/') {
				String command = input.substring(1);

				if (command.equals("exit")) {
					break;
				} else if (command.equals("clear")) {
					terminalService.clearTerminal();
				}
			} else if (!input.isEmpty()) {
				terminalService.printMessage("test", input);
			}
		}
	}
}
