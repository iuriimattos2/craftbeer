package com.beerhouse.craftbeer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beerhouse.craftbeer.domain.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {

}
