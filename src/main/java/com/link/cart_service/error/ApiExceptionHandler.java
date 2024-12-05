package com.link.cart_service.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApiBaseException.class)
	public ResponseEntity<ErrorDetails> handleError(ApiBaseException ex, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, ex.getStatusCode());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorValidation errorValidation = new ErrorValidation();
		errorValidation.setUri(request.getDescription(false));

		ex.getBindingResult().getFieldErrors().forEach(fe -> {
			errorValidation.addError(fe.getDefaultMessage());
		});

		return new ResponseEntity<>(errorValidation, status);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetails> handleError(ConstraintViolationException ex, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
