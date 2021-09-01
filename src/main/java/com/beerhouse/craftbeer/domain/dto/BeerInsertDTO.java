package com.beerhouse.craftbeer.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class BeerInsertDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Beer name is required.")
	private String name;

	@NotBlank(message = "Beer Ingredients is required.")
	private String ingredients;

	@NotBlank(message = "Beer Alcohol Content is required.")
	private String alcoholContent;

	@NotNull(message = "Beer Price is required.")
	@Min(value = 0, message = "Beer Price must be greater than 0.")
	private Number price;

	@NotBlank(message = "Beer Category is required.")
	private String category;
}
