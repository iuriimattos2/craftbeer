package com.beerhouse.craftbeer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.repository.BeerRepository;
import com.beerhouse.craftbeer.service.exception.ObjectNotFoundException;
import com.beerhouse.craftbeer.service.util.TextBuilderService;

@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;

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
					textBuilderService.getDescriptionForNotFoundRegister("Beer", "id", id.toString()));
		}
		return optBeer.get();
	}
}
