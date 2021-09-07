package com.beerhouse.craftbeer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.domain.dto.BeerPartialUpdateDTO;
import com.beerhouse.craftbeer.domain.dto.BeerValidationDTO;
import com.beerhouse.craftbeer.repository.BeerRepository;
import com.beerhouse.craftbeer.service.exception.GenericException;
import com.beerhouse.craftbeer.service.exception.ObjectNotFoundException;
import com.beerhouse.craftbeer.service.util.TextBuilderService;
import com.beerhouse.craftbeer.service.validator.BeerValidator;

@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;

	@Autowired
	private BeerValidator beerValidator;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Beer> findAll() {
		return beerRepository.findAll();
	}

	public Page<Beer> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return beerRepository.findAll(pageRequest);
	}

	public Beer findById(Integer id) {
		Optional<Beer> optBeer = beerRepository.findById(id);
		if (optBeer.isEmpty()) {
			throw new ObjectNotFoundException(
					textBuilderService.getExceptionDescriptionForNotFoundRegister("Beer", "id", id.toString()));
		}
		return optBeer.get();
	}

	public Optional<Beer> findByName(String name) {
		return beerRepository.findByName(name);
	}

	public Beer insert(BeerValidationDTO beerValidationDTO) {
		beerValidator.validate(beerValidationDTO.getName(), 0);

		Beer beer = new Beer(beerValidationDTO.getName(), beerValidationDTO.getIngredients(),
				beerValidationDTO.getAlcoholContent(), beerValidationDTO.getPrice(), beerValidationDTO.getCategory());
		return save(beer);

	}

	public Beer updateById(Integer id, BeerValidationDTO beerValidationDTO) {
		Beer beer = findById(id);
		beerValidator.validate(beerValidationDTO.getName(), id);

		beer.setName(beerValidationDTO.getName());
		beer.setIngredients(beerValidationDTO.getIngredients());
		beer.setAlcoholContent(beerValidationDTO.getAlcoholContent());
		beer.setPrice(beerValidationDTO.getPrice());
		beer.setCategory(beerValidationDTO.getCategory());

		return save(beer);
	}

	public Beer updatePartialById(Integer id, BeerPartialUpdateDTO beerDTO) {
		Beer beer = findById(id);

		if (validateStringValue(beer.getName(), beerDTO.getName())) {
			beerValidator.validate(beerDTO.getName(), id);
			beer.setName(beerDTO.getName());
		}
		if (validateStringValue(beer.getIngredients(), beerDTO.getIngredients())) {
			beer.setIngredients(beerDTO.getIngredients());
		}
		if (validateStringValue(beer.getAlcoholContent(), beerDTO.getAlcoholContent())) {
			beer.setAlcoholContent(beerDTO.getAlcoholContent());
		}
		if (beer.getPrice() != beerDTO.getPrice() && beerDTO.getPrice() != null) {
			beer.setPrice(beerDTO.getPrice());
		}
		if (validateStringValue(beer.getCategory(), beerDTO.getCategory())) {
			beer.setCategory(beerDTO.getCategory());
		}

		return save(beer);
	}

	private boolean validateStringValue(String oldValue, String newValue) {
		if (oldValue != newValue && newValue != null && !newValue.isBlank()) {
			return true;
		}
		return false;
	}

	private Beer save(Beer beer) {
		try {
			return beerRepository.save(beer);
		} catch (Exception e) {
			String[] parameters = { beer.toString() };
			throw new GenericException(
					textBuilderService.getExceptionDescriptionForMultiParameters("Beer", parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		try {
			beerRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(
					textBuilderService.getExceptionDescriptionForMultiParameters("Beer", parameters, e.toString()));
		}
	}
}
