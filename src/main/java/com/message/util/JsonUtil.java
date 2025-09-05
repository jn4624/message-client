package com.message.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.service.TerminalService;

public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static TerminalService terminalService;

	public static void setTerminalService(TerminalService terminalService) {
		JsonUtil.terminalService = terminalService;
	}

	public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
		try {
			return Optional.of(objectMapper.readValue(json, clazz));
		} catch (Exception e) {
			terminalService.printSystemMessage("Failed JSON to Object: " + e.getMessage());
			return Optional.empty();
		}
	}

	public static Optional<String> toJson(Object object) {
		try {
			return Optional.of(objectMapper.writeValueAsString(object));
		} catch (Exception e) {
			terminalService.printSystemMessage("Failed Object to JSON: " + e.getMessage());
			return Optional.empty();
		}
	}
}
