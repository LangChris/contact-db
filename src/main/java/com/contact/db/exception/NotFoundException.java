package com.contact.db.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(final String message) {
		super(message);
	}
}
