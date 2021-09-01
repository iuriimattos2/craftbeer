package com.beerhouse.craftbeer.service.validator.util;

import java.util.ArrayList;
import java.util.List;

import com.beerhouse.craftbeer.service.exception.GenericValidationException;

import lombok.Getter;

@Getter
public class Validator {

	private List<Boolean> listTrue = new ArrayList<Boolean>();
	private List<Boolean> listFalse = new ArrayList<Boolean>();
	private List<String> listMessage = new ArrayList<String>();

	public void validateOne(ValidatorReturn validatorReturn) {
		Boolean validated = validatorReturn.getValidated();
		String message = validatorReturn.getMessage();

		if (validated) {
			listTrue.add(validated);
		} else {
			listFalse.add(validated);
			listMessage.add(message);
		}
	}

	public void validateResult(Validator validator) {
		if (validator.getListFalse().size() > 0) {
			throw new GenericValidationException("Occurred violations in business rules.", validator.getListMessage());
		}
	}
}
