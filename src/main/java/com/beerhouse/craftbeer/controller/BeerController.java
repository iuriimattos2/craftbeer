package com.beerhouse.craftbeer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.domain.dto.BeerPartialUpdateDTO;
import com.beerhouse.craftbeer.domain.dto.BeerValidationDTO;
import com.beerhouse.craftbeer.service.BeerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/v1/beer")
public class BeerController {

	@Autowired
	private BeerService beerService;

	@ApiOperation(value = "(findAll) Find all registers of Beers.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Beer>> findAll() {
		return ResponseEntity.ok().body(beerService.findAll());
	}

	@ApiOperation(value = "(findAllPaginated) Find all registers of Beers paginated.")
	@RequestMapping(method = RequestMethod.GET, path = "/paginated")
	public ResponseEntity<Page<Beer>> findAllPaginated(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "24") Integer linesPerPage,
			@RequestParam(defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String orderBy) {
		return ResponseEntity.ok().body(beerService.findAllPaginated(page, linesPerPage, direction, orderBy));
	}

	@ApiOperation(value = "(findById) Find a register of Beer by Identificator.")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Beer> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(beerService.findById(id));
	}

	@ApiOperation(value = "(insert) Insert a new register of Beer.")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Beer> insert(@Valid @RequestBody BeerValidationDTO beerValidationDTO) {
		Beer beer = beerService.insert(beerValidationDTO);
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(beer.getId()).toUri())
				.body(beer);
	}

	@ApiOperation(value = "(updateById) Update a register of Beer.")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Beer> updateById(@Valid @RequestBody BeerValidationDTO beerValidationDTO,
			@PathVariable Integer id) {
		return ResponseEntity.ok().body(beerService.updateById(id, beerValidationDTO));
	}

	@ApiOperation(value = "(updatePartialById) Partially update of a Beer.")
	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public ResponseEntity<Beer> updatePartialById(@Valid @RequestBody BeerPartialUpdateDTO beerDTO,
			@PathVariable Integer id) {
		return ResponseEntity.ok().body(beerService.updatePartialById(id, beerDTO));
	}

	@ApiOperation(value = "(deleteById) Delete a Beer register by Identificator.")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		beerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
