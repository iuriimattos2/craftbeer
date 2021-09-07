package com.beerhouse.craftbeer.service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.beerhouse.TestUtils;
import com.beerhouse.craftbeer.service.BeerService;
import com.beerhouse.craftbeer.service.exception.GenericValidationException;
import com.beerhouse.craftbeer.service.util.TextBuilderService;

public class BeerValidatorTest {

	@InjectMocks
	private BeerValidator beerValidator;

	@Mock
	private BeerService beerService;

	@Mock
	private TextBuilderService textBuilderService;

	private TestUtils testUtils;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		testUtils = new TestUtils();
	}

	@Test
	@DisplayName("Test should pass when validate doesn't throws any Exception in Insert case")
	public void testValidateInInsertValidatesOk() {
		when(beerService.findByName(testUtils.BEER_NAME)).thenReturn(Optional.empty());

		beerValidator.validate(testUtils.BEER_NAME, 0);

		verify(beerService, times(1)).findByName(testUtils.BEER_NAME);
	}

	@Test
	@DisplayName("Test should pass when validate throws GenericValidationException in Insert case")
	public void testValidateInInsertThrowsGenericValidationException() {
		String exceptionText = "Occurred violations in business rules.";
		List<String> exceptionDescriptions = new ArrayList<String>();
		exceptionDescriptions.add("Duplicated object");

		int mockBeerId = 1;
		when(textBuilderService.getExceptionDescriptionForMultiRegister("Beer", "name", testUtils.BEER_NAME))
				.thenReturn("Duplicated object");
		when(beerService.findByName(testUtils.BEER_NAME)).thenReturn(Optional.of(testUtils.getMockBeer(mockBeerId)));

		GenericValidationException genericValidationException = assertThrows(GenericValidationException.class,
				() -> beerValidator.validate(testUtils.BEER_NAME, 0));

		assertEquals(exceptionText, genericValidationException.getMessage());
		assertEquals(exceptionDescriptions, genericValidationException.getErrors());
		verify(beerService, times(1)).findByName(testUtils.BEER_NAME);
	}

	@Test
	@DisplayName("Test should pass when validate doesn't throws any Exception in Update case")
	public void testValidateInUpdateValidatesOk() {
		when(beerService.findByName(testUtils.BEER_NAME)).thenReturn(Optional.empty());

		beerValidator.validate(testUtils.BEER_NAME, 1);

		verify(beerService, times(1)).findByName(testUtils.BEER_NAME);
	}

	@Test
	@DisplayName("Test should pass when validate throws GenericValidationException in Update case")
	public void testValidateInUpdateThrowsGenericValidationException() {
		String exceptionText = "Occurred violations in business rules.";
		List<String> exceptionDescriptions = new ArrayList<String>();
		exceptionDescriptions.add("Duplicated object");

		int mockBeerId = 2;
		when(textBuilderService.getExceptionDescriptionForMultiRegister("Beer", "name", testUtils.BEER_NAME))
				.thenReturn("Duplicated object");
		when(beerService.findByName(testUtils.BEER_NAME)).thenReturn(Optional.of(testUtils.getMockBeer(1)));

		GenericValidationException genericValidationException = assertThrows(GenericValidationException.class,
				() -> beerValidator.validate(testUtils.BEER_NAME, mockBeerId));

		assertEquals(exceptionText, genericValidationException.getMessage());
		assertEquals(exceptionDescriptions, genericValidationException.getErrors());
		verify(beerService, times(1)).findByName(testUtils.BEER_NAME);
	}
}
