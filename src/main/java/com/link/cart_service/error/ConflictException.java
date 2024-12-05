package com.link.cart_service.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(message);
	}

	@Override
	protected HttpStatus getStatusCode() {
		return HttpStatus.CONFLICT;
	}
}
