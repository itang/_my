package com.packtpub.felix.bookshelf.inventory.api;

public class BookNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super();
	}

	public BookNotFoundException(String message) {
		super(message);
	}

}