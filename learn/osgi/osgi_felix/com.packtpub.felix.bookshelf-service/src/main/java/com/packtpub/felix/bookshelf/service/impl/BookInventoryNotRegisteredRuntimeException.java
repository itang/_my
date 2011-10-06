package com.packtpub.felix.bookshelf.service.impl;

public class BookInventoryNotRegisteredRuntimeException extends
		RuntimeException {
	private static final long serialVersionUID = 1L;

	public BookInventoryNotRegisteredRuntimeException(String name) {
		super("Error: " + name);
	}
}
