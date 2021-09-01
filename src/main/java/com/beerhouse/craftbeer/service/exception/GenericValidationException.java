package com.beerhouse.craftbeer.service.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<String> errors;

	public GenericValidationException(String msg) {
		super(msg);
	}

	public GenericValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GenericValidationException(String msg, Throwable cause, List<String> errors) {
		super(msg, cause);
		this.errors = errors;
	}

	public GenericValidationException(String msg, List<String> errors) {
		super(msg);
		this.errors = errors;
	}
}
