package com.contact.db.exception;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -6L;

	public ServerException() {
		super();
	}
	
	public ServerException(final String message) {
		super(message);
	}
}
