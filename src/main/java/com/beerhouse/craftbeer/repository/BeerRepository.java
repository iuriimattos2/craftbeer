package com.beerhouse.craftbeer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beerhouse.craftbeer.domain.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {

	@Transactional(readOnly = true)
	Optional<Beer> findByName(String name);

}
