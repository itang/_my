package com.packtpub.felix.bookshelf.inventory.api;

public class InvalidBookException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidBookException() {
		super();
	}

	public InvalidBookException(String message) {
		super(message);
	}
}