package com.beerhouse.craftbeer.service.validator.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatorReturn {

	private Boolean validated;
	private String message;

	public ValidatorReturn() {
		this.validated = true;
		this.message = null;
	}

	public ValidatorReturn(String message) {
		this.validated = false;
		this.message = message;
	}
}
