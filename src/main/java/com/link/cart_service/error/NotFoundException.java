package com.link.cart_service.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}

	@Override
	protected HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}
}
