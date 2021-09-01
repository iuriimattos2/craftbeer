package com.beerhouse.craftbeer.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.beerhouse.craftbeer.service.exception.GenericException;
import com.beerhouse.craftbeer.service.exception.GenericValidationException;
import com.beerhouse.craftbeer.service.exception.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpServletRequest request) {
		StandardError standardError = new StandardError(System.currentTimeMillis(),
				HttpStatus.METHOD_NOT_ALLOWED.value(), "HTTP method not supported in requested URL", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(standardError);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<StandardError> genericException(GenericException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(System.currentTimeMillis(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generic error", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object not found", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		ValidationError validationError = new ValidationError(System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error - Invalid data", e.getMessage(),
				request.getRequestURI());
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
	}

	@ExceptionHandler(GenericValidationException.class)
	public ResponseEntity<StandardError> genericValidation(GenericValidationException e, HttpServletRequest request) {
		ValidationError validationError = new ValidationError(System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error - Business rules", e.getMessage(),
				request.getRequestURI());
		for (String errorItem : e.getErrors()) {
			validationError.addError("Item to be corrected", errorItem);
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
	}
}