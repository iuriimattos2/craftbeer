package com.beerhouse.craftbeer.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BeerValidationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Beer name is required.")
	private String name;

	@NotBlank(message = "Beer Ingredients is required.")
	private String ingredients;

	@NotBlank(message = "Beer Alcohol Content is required.")
	private String alcoholContent;

	@NotNull(message = "Beer Price is required.")
	@Min(value = 0, message = "Beer Price must be greater than 0.")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	@NotBlank(message = "Beer Category is required.")
	private String category;
}
