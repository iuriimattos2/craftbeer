package com.beerhouse.craftbeer.service.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.domain.dto.BeerInsertDTO;
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

	private ValidatorReturn validateByName(String name) {
		Optional<Beer> optBeer = beerService.findByName(name);
		return (optBeer.isPresent()
				? new ValidatorReturn(textBuilderService.getExceptionDescriptionForMultiRegister(entity, "name", name))
				: new ValidatorReturn());
	}

	public void validateInsert(BeerInsertDTO beerInsertDTO) {
		Validator validator = new Validator();
		validator.validateOne(validateByName(beerInsertDTO.getName().trim()));
		validator.validateResult(validator);
	}

}
