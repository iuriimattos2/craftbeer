package com.beerhouse.craftbeer.service.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.service.BeerService;
import com.beerhouse.craftbeer.service.util.TextBuilderService;
import com.beerhouse.craftbeer.service.validator.util.Validator;
import com.beerhouse.craftbeer.service.validator.util.ValidatorReturn;

@Service
public class BeerValidator {

	@Autowired
	private BeerService beerService;

	@Autowired
	private TextBuilderService textBuilderService;

	private String entity = "Beer";

	private ValidatorReturn validateByName(String name, Integer id) {
		Optional<Beer> optBeer = beerService.findByName(name);
		if (id == 0) {
			return (optBeer.isPresent()
					? new ValidatorReturn(
							textBuilderService.getExceptionDescriptionForMultiRegister(entity, "name", name))
					: new ValidatorReturn());
		} else {
			return ((optBeer.isPresent() && !optBeer.get().getId().equals(id))
					? new ValidatorReturn(
							textBuilderService.getExceptionDescriptionForMultiRegister(entity, "name", name))
					: new ValidatorReturn());
		}
	}

	public void validate(String beerName, Integer id) {
		Validator validator = new Validator();
		validator.validateOne(validateByName(beerName.trim(), id));
		validator.validateResult(validator);
	}

}
