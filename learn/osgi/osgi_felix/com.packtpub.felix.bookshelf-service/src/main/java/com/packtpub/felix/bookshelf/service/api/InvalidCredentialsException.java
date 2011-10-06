package com.packtpub.felix.bookshelf.service.api;

public class InvalidCredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
	}

	public InvalidCredentialsException(String username) {
		super("Error: " + username);
	}

}
