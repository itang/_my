package com.packtpub.felix.bookshelf.service.tui;

public class BookshelfServiceNotRegisteredRuntimeException extends
		RuntimeException {
	private static final long serialVersionUID = 1L;

	public BookshelfServiceNotRegisteredRuntimeException(String name) {
		super("Error: " + name);
	}
}
