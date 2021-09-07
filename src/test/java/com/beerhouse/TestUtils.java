package com.beerhouse;

import java.math.BigDecimal;

import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.domain.dto.BeerValidationDTO;

public class TestUtils {

	public final String BEER_NAME = "BeerNameTest";
	public final String BEER_INGREDIENTS = "BeerIngredientsTest";
	public final String BEER_ALCOHOL_CONTENT = "BeerAlcoholContentTest";
	public final BigDecimal BEER_PRICE = new BigDecimal(7.99);
	public final String BEER_CATEGORY = "BeerCategoryTest";

	public Beer getMockBeer(Integer id) {
		Beer mockBeer = new Beer(BEER_NAME, BEER_INGREDIENTS, BEER_ALCOHOL_CONTENT, BEER_PRICE, BEER_CATEGORY);
		if (id != 0) {
			mockBeer.setId(id);
		}
		return mockBeer;
	}

	public BeerValidationDTO getMockBeerValidationDTO() {
		BeerValidationDTO mockBeerValidationDTO = new BeerValidationDTO(BEER_NAME, BEER_INGREDIENTS,
				BEER_ALCOHOL_CONTENT, BEER_PRICE, BEER_CATEGORY);
		return mockBeerValidationDTO;
	}

}
