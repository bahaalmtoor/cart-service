package com.link.cart_service.error;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiBaseException(String message) {
		super(message);
	}

	protected abstract HttpStatus getStatusCode();
}
