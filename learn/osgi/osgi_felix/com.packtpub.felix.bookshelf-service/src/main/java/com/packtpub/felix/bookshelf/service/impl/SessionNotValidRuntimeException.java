package com.packtpub.felix.bookshelf.service.impl;

public class SessionNotValidRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SessionNotValidRuntimeException() {

	}

	public SessionNotValidRuntimeException(String sessionId) {
		super("Error: " + sessionId);
	}

}
