package com.beerhouse.craftbeer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.service.BeerService;

@RestController
@RequestMapping(path = "/api/v1/beer")
public class BeerController {

	@Autowired
	private BeerService beerService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Beer>> findAll() {
		return ResponseEntity.ok().body(beerService.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/paginated")
	public ResponseEntity<Page<Beer>> findAllPaginated(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "24") Integer linesPerPage,
			@RequestParam(defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String orderBy) {
		return ResponseEntity.ok().body(beerService.findAllPaginated(page, linesPerPage, direction, orderBy));
	}
}
