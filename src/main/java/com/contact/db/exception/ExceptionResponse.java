package com.contact.db.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private HttpStatus status;
	private String message;
	private final String timestamp = new SimpleDateFormat("MM/dd/yyyy.HH.mm.ss").format(new Date());
	private String uri;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
}
