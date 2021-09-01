package com.beerhouse.craftbeer.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}