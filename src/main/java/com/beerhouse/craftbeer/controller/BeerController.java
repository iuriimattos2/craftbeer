package com.beerhouse.craftbeer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.craftbeer.service.BeerService;

@RestController
@RequestMapping(path = "/api/v1/beer")
public class BeerController {

	@Autowired
	private BeerService beerService;

}
