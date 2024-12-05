package com.link.cart_service.error;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDetails {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorDetails.class);
	
	private String message;
	private String uri;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;

	public ErrorDetails() {
		timestamp = new Date();
	}

	public ErrorDetails(String message, String uri) {
		this();
		this.message = message;
		this.uri = uri;
		logErrorDetails();
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	protected void logErrorDetails() {
		LOGGER.error("\nmessage: {}\nuri: {}\ntimestamp: {}", this.message, this.uri, this.timestamp);
	}
}
