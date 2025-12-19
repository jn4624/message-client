package com.message.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

public class TerminalService {

	private Terminal terminal;
	private LineReader lineReader;

	/*
	  생성자를 private 접근 제어자로 만드는 이유
	  - 정적 팩토리 메서드를 사용하여 객체를 생성하도록 하기 위함이다.
	  생성자를 사용하여 객체를 생성하지 않는 이유
	  - Terminal을 초기화할 때 예외 발생 가능성이 있기 때문인데,
	    생성자에서 예외를 던지는건 좋은 선택이 아니기 때문이다.
	 */
	private TerminalService() {
	}

	public static TerminalService create() throws IOException {
		TerminalService terminalService = new TerminalService();

		try {
			/*
			  system 메서드의 true/false
			  - true: OS의 터미널 사용
			  - false: jline이 제공하는 더미 터미널 사용
			 */
			terminalService.terminal = TerminalBuilder.builder().system(true).build();
		} catch (IOException e) {
			System.err.println("Failed to create TerminalService. error: " + e.getMessage());
			throw e;
		}

		terminalService.lineReader =
			LineReaderBuilder.builder()
				.terminal(terminalService.terminal)
				.variable(LineReader.HISTORY_FILE, Paths.get("./data/history.txt"))
				.build();

		return terminalService;
	}

	public String readLine(String prompt) {
		String input = lineReader.readLine(prompt);
		terminal.puts(InfoCmp.Capability.cursor_up);
		terminal.puts(InfoCmp.Capability.delete_line);
		terminal.flush();
		return input;
	}

	public void printMessage(String username, String content) {
		lineReader.printAbove("%s: %s".formatted(username, content));
	}

	public void printSystemMessage(String content) {
		lineReader.printAbove("=> " + content);
	}

	public void clearTerminal() {
		terminal.puts(InfoCmp.Capability.clear_screen);
		terminal.flush();
	}
}
