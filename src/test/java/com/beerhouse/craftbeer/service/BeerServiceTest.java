package com.beerhouse.craftbeer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.beerhouse.TestUtils;
import com.beerhouse.craftbeer.domain.Beer;
import com.beerhouse.craftbeer.domain.dto.BeerPartialUpdateDTO;
import com.beerhouse.craftbeer.domain.dto.BeerValidationDTO;
import com.beerhouse.craftbeer.repository.BeerRepository;
import com.beerhouse.craftbeer.service.exception.GenericValidationException;
import com.beerhouse.craftbeer.service.exception.ObjectNotFoundException;
import com.beerhouse.craftbeer.service.util.TextBuilderService;
import com.beerhouse.craftbeer.service.validator.BeerValidator;

public class BeerServiceTest {

	@InjectMocks
	private BeerService beerService;

	@Mock
	private BeerRepository beerRepository;

	@Mock
	private TextBuilderService textBuilderService;

	@Mock
	private BeerValidator beerValidator;

	private TestUtils testUtils;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		testUtils = new TestUtils();
	}

	private void assertBeer(Beer result, int expectedId) {
		assertEquals(expectedId, result.getId());
		assertEquals(testUtils.BEER_NAME, result.getName());
		assertEquals(testUtils.BEER_INGREDIENTS, result.getIngredients());
		assertEquals(testUtils.BEER_ALCOHOL_CONTENT, result.getAlcoholContent());
		assertEquals(testUtils.BEER_PRICE, result.getPrice());
		assertEquals(testUtils.BEER_CATEGORY, result.getCategory());
	}

	private void assertUpdateBeer(Beer result, int expectedId) {
		assertEquals(expectedId, result.getId());
		assertEquals(testUtils.BEER_NAME + "Update", result.getName());
		assertEquals(testUtils.BEER_INGREDIENTS + "Update", result.getIngredients());
		assertEquals(testUtils.BEER_ALCOHOL_CONTENT + "Update", result.getAlcoholContent());
		assertEquals(testUtils.BEER_PRICE.add(testUtils.BEER_PRICE), result.getPrice());
		assertEquals(testUtils.BEER_CATEGORY + "Update", result.getCategory());
	}

	@Test
	@DisplayName("Test should pass when findAll returns a list of Beers")
	public void testFindAllMustReturnsListOfBeers() {
		List<Beer> expected = Arrays.asList(testUtils.getMockBeer(1), testUtils.getMockBeer(2));

		when(beerRepository.findAll()).thenReturn(expected);

		List<Beer> result = beerService.findAll();

		assertEquals(2, result.size());
		assertEquals(expected, result);
		verify(beerRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Test should pass when findAllPaginated returns a Page object of Beers")
	public void testFindAllPaginatedMustReturnsListOfBeersPaginated() {
		Page<Beer> expected = new PageImpl<Beer>(Arrays.asList(testUtils.getMockBeer(1), testUtils.getMockBeer(2)));

		Integer page = 0;
		Integer linesPerPage = 24;
		String direction = "ASC";
		String orderBy = "id";

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		when(beerRepository.findAll(pageRequest)).thenReturn(expected);

		Page<Beer> result = beerService.findAllPaginated(page, linesPerPage, direction, orderBy);

		assertEquals(2, result.getTotalElements());
		assertEquals(expected, result);
		verify(beerRepository, times(1)).findAll(pageRequest);
	}

	@Test
	@DisplayName("Test should pass when findById returns a correctly Beer")
	public void testFindByIdMustReturnsBeer() {
		int mockBeerId = 1;
		Beer mockBeer = testUtils.getMockBeer(mockBeerId);

		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.of(mockBeer));

		Beer result = beerService.findById(mockBeerId);

		assertBeer(result, mockBeerId);
		verify(beerRepository, times(1)).findById(mockBeerId);
	}

	@Test
	@DisplayName("Test should pass when findById throws ObjectNotFoundException")
	public void testFindByIdMustThrowsObjectNotFoundException() {
		String exceptionText = "ObjectNotFoundException";

		when(textBuilderService.getExceptionDescriptionForNotFoundRegister("Beer", "id", "1"))
				.thenReturn(exceptionText);
		when(beerRepository.findById(1)).thenReturn(Optional.empty());

		ObjectNotFoundException objectNotFoundException = assertThrows(ObjectNotFoundException.class,
				() -> beerService.findById(1));

		assertEquals(exceptionText, objectNotFoundException.getMessage());
		verify(beerRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("Test should pass when insert returns inserted object correctly")
	public void testInsertMustReturnsInsertedObject() {
		Beer toSave = testUtils.getMockBeer(0);

		doNothing().when(beerValidator).validate(testUtils.BEER_NAME, 0);
		when(beerRepository.save(toSave)).thenReturn(testUtils.getMockBeer(1));

		Beer result = beerService.insert(testUtils.getMockBeerValidationDTO());

		assertBeer(result, 1);
		verify(beerRepository, times(1)).save(toSave);
		verify(beerValidator, times(1)).validate(testUtils.BEER_NAME, 0);
	}

	@Test
	@DisplayName("Test should pass when insert throws GenericValidationException")
	public void testInsertMustThrowsGenericValidationException() {
		String exceptionText = "GenericValidationException";

		// Inserting incorrect register. Duplicated Beer Name.
		Beer toSave = testUtils.getMockBeer(0);

		doThrow(new GenericValidationException(exceptionText)).when(beerValidator).validate(testUtils.BEER_NAME, 0);

		GenericValidationException genericValidationException = assertThrows(GenericValidationException.class,
				() -> beerService.insert(testUtils.getMockBeerValidationDTO()));

		assertEquals(exceptionText, genericValidationException.getMessage());
		verify(beerRepository, times(0)).save(toSave);
		verify(beerValidator, times(1)).validate(testUtils.BEER_NAME, 0);
	}

	@Test
	@DisplayName("Test should pass when updateById returns updated object correctly")
	public void testUpdateByIdMustReturnsUpdatedObject() {
		int mockBeerId = 1;

		Beer oldBeer = testUtils.getMockBeer(mockBeerId);
		Beer newBeer = testUtils.getMockBeer(mockBeerId);
		newBeer.setName(testUtils.BEER_NAME + "Update");
		newBeer.setIngredients(testUtils.BEER_INGREDIENTS + "Update");
		newBeer.setAlcoholContent(testUtils.BEER_ALCOHOL_CONTENT + "Update");
		newBeer.setPrice(testUtils.BEER_PRICE.add(testUtils.BEER_PRICE));
		newBeer.setCategory(testUtils.BEER_CATEGORY + "Update");

		BeerValidationDTO newBeerDto = new BeerValidationDTO(newBeer.getName(), newBeer.getIngredients(),
				newBeer.getAlcoholContent(), newBeer.getPrice(), newBeer.getCategory());

		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.of(oldBeer));
		doNothing().when(beerValidator).validate(newBeerDto.getName(), mockBeerId);
		when(beerRepository.save(newBeer)).thenReturn(newBeer);

		Beer result = beerService.updateById(mockBeerId, newBeerDto);

		assertUpdateBeer(result, 1);
		verify(beerRepository, times(1)).save(newBeer);
		verify(beerRepository, times(1)).findById(mockBeerId);
		verify(beerValidator, times(1)).validate(newBeerDto.getName(), mockBeerId);
	}

	@Test
	@DisplayName("Test should pass when updateById throws ObjectNotFoundException")
	public void testUpdateByIdMustThrowsObjectNotFoundException() {
		String exceptionText = "ObjectNotFoundException";

		Beer newBeer = testUtils.getMockBeer(1);
		newBeer.setName(testUtils.BEER_NAME + "Update");
		newBeer.setIngredients(testUtils.BEER_INGREDIENTS + "Update");
		newBeer.setAlcoholContent(testUtils.BEER_ALCOHOL_CONTENT + "Update");
		newBeer.setPrice(testUtils.BEER_PRICE.add(testUtils.BEER_PRICE));
		newBeer.setCategory(testUtils.BEER_CATEGORY + "Update");

		BeerValidationDTO newBeerDto = new BeerValidationDTO(newBeer.getName(), newBeer.getIngredients(),
				newBeer.getAlcoholContent(), newBeer.getPrice(), newBeer.getCategory());

		when(textBuilderService.getExceptionDescriptionForNotFoundRegister("Beer", "id", "1"))
				.thenReturn(exceptionText);
		when(beerRepository.findById(1)).thenReturn(Optional.empty());

		ObjectNotFoundException objectNotFoundException = assertThrows(ObjectNotFoundException.class,
				() -> beerService.updateById(1, testUtils.getMockBeerValidationDTO()));

		assertEquals(exceptionText, objectNotFoundException.getMessage());
		verify(beerRepository, times(1)).findById(1);
		verify(beerValidator, times(0)).validate(newBeerDto.getName(), 1);
		verify(beerRepository, times(0)).save(newBeer);
	}

	@Test
	@DisplayName("Test should pass when updateById throws GenericValidationException")
	public void testUpdateByIdMustThrowsGenericValidationException() {
		int mockBeerId = 1;
		String exceptionText = "GenericValidationException";

		Beer oldBeer = testUtils.getMockBeer(mockBeerId);
		Beer newBeer = testUtils.getMockBeer(mockBeerId);
		newBeer.setName(testUtils.BEER_NAME + "Update");
		newBeer.setIngredients(testUtils.BEER_INGREDIENTS + "Update");
		newBeer.setAlcoholContent(testUtils.BEER_ALCOHOL_CONTENT + "Update");
		newBeer.setPrice(testUtils.BEER_PRICE.add(testUtils.BEER_PRICE));
		newBeer.setCategory(testUtils.BEER_CATEGORY + "Update");

		BeerValidationDTO newBeerDto = new BeerValidationDTO(newBeer.getName(), newBeer.getIngredients(),
				newBeer.getAlcoholContent(), newBeer.getPrice(), newBeer.getCategory());

		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.of(oldBeer));
		doThrow(new GenericValidationException(exceptionText)).when(beerValidator).validate(newBeerDto.getName(),
				mockBeerId);

		GenericValidationException genericValidationException = assertThrows(GenericValidationException.class,
				() -> beerService.updateById(mockBeerId, newBeerDto));

		assertEquals(exceptionText, genericValidationException.getMessage());

		verify(beerRepository, times(0)).save(newBeer);
		verify(beerRepository, times(1)).findById(mockBeerId);
		verify(beerValidator, times(1)).validate(newBeerDto.getName(), mockBeerId);
	}

	@Test
	public void testUpdatePartialByIdMustReturnsUpdatedObject() {
		int mockBeerId = 1;
		String newName = testUtils.BEER_NAME + "UpdatePartial";
		String newIngredients = " ";
		String newCategory = null;

		Beer oldBeer = testUtils.getMockBeer(mockBeerId);
		Beer newBeer = testUtils.getMockBeer(mockBeerId);
		newBeer.setName(newName);

		BeerPartialUpdateDTO newBeerDto = new BeerPartialUpdateDTO();
		newBeerDto.setName(newName);
		newBeerDto.setIngredients(newIngredients);
		newBeerDto.setCategory(newCategory);

		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.of(oldBeer));
		doNothing().when(beerValidator).validate(newBeerDto.getName(), mockBeerId);
		when(beerRepository.save(newBeer)).thenReturn(newBeer);

		Beer result = beerService.updatePartialById(mockBeerId, newBeerDto);

		assertEquals(mockBeerId, result.getId());
		assertEquals(newName, result.getName());
		assertEquals(testUtils.BEER_INGREDIENTS, result.getIngredients());
		assertEquals(testUtils.BEER_ALCOHOL_CONTENT, result.getAlcoholContent());
		assertEquals(testUtils.BEER_PRICE, result.getPrice());
		assertEquals(testUtils.BEER_CATEGORY, result.getCategory());

		verify(beerRepository, times(1)).save(newBeer);
		verify(beerRepository, times(1)).findById(mockBeerId);
		verify(beerValidator, times(1)).validate(newBeerDto.getName(), mockBeerId);
	}

	@Test
	public void testUpdatePartialByIdMustThrowsObjectNotFoundException() {
		String exceptionText = "ObjectNotFoundException";

		Integer mockBeerId = 1;
		String newName = testUtils.BEER_NAME + "UpdatePartial";
		String newIngredients = " ";
		String newCategory = null;

		Beer newBeer = testUtils.getMockBeer(mockBeerId);
		newBeer.setName(newName);

		BeerPartialUpdateDTO newBeerDto = new BeerPartialUpdateDTO();
		newBeerDto.setName(newName);
		newBeerDto.setIngredients(newIngredients);
		newBeerDto.setCategory(newCategory);

		when(textBuilderService.getExceptionDescriptionForNotFoundRegister("Beer", "id", mockBeerId + ""))
				.thenReturn(exceptionText);
		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.empty());

		ObjectNotFoundException objectNotFoundException = assertThrows(ObjectNotFoundException.class,
				() -> beerService.updatePartialById(mockBeerId, newBeerDto));

		assertEquals(exceptionText, objectNotFoundException.getMessage());
		verify(beerRepository, times(1)).findById(mockBeerId);
		verify(beerValidator, times(0)).validate(newBeerDto.getName(), mockBeerId);
		verify(beerRepository, times(0)).save(newBeer);
	}

	@Test
	public void testUpdatePartialByIdMustThrowsGenericValidationException() {
		String exceptionText = "GenericValidationException";

		int mockBeerId = 1;
		String newName = testUtils.BEER_NAME + "UpdatePartial";
		String newIngredients = " ";
		String newCategory = null;

		Beer oldBeer = testUtils.getMockBeer(mockBeerId);
		Beer newBeer = testUtils.getMockBeer(mockBeerId);
		newBeer.setName(newName);

		BeerPartialUpdateDTO newBeerDto = new BeerPartialUpdateDTO();
		newBeerDto.setName(newName);
		newBeerDto.setIngredients(newIngredients);
		newBeerDto.setCategory(newCategory);

		when(beerRepository.findById(mockBeerId)).thenReturn(Optional.of(oldBeer));
		doThrow(new GenericValidationException(exceptionText)).when(beerValidator).validate(newBeerDto.getName(),
				mockBeerId);

		GenericValidationException genericValidationException = assertThrows(GenericValidationException.class,
				() -> beerService.updatePartialById(mockBeerId, newBeerDto));

		assertEquals(exceptionText, genericValidationException.getMessage());

		verify(beerRepository, times(0)).save(newBeer);
		verify(beerRepository, times(1)).findById(mockBeerId);
		verify(beerValidator, times(1)).validate(newBeerDto.getName(), mockBeerId);
	}

	@Test
	@DisplayName("Test should pass when deleteById returns nothing")
	public void testDeleteByIdMustReturnsNothing() {
		when(beerRepository.findById(1)).thenReturn(Optional.of(testUtils.getMockBeer(1)));
		doNothing().when(beerRepository).deleteById(1);
		beerService.deleteById(1);
		verify(beerRepository, times(1)).findById(1);
		verify(beerRepository, times(1)).deleteById(1);

	}

	@Test
	@DisplayName("Test should pass when deleteById throws ObjectNotFoundException")
	public void testDeleteByIdMustThrowsObjectNotFoundException() {
		String exceptionText = "ObjectNotFoundException";

		when(textBuilderService.getExceptionDescriptionForNotFoundRegister("Beer", "id", "1"))
				.thenReturn(exceptionText);
		when(beerRepository.findById(1)).thenReturn(Optional.empty());

		ObjectNotFoundException objectNotFoundException = assertThrows(ObjectNotFoundException.class,
				() -> beerService.deleteById(1));

		assertEquals(exceptionText, objectNotFoundException.getMessage());
		verify(beerRepository, times(1)).findById(1);
		verify(beerRepository, times(0)).deleteById(1);
	}
}
