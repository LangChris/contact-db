package com.contact.db.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -6L;

	public BadRequestException() {
		super();
	}
	
	public BadRequestException(final String message) {
		super(message);
	}
}
