package com.beerhouse.craftbeer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.craftbeer.repository.BeerRepository;

@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;

}
