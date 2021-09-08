package com.beerhouse.craftbeer.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BeerValidationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true)
	@NotBlank(message = "Beer name is required.")
	private String name;

	@ApiModelProperty(position = 2, required = true)
	@NotBlank(message = "Beer Ingredients is required.")
	private String ingredients;

	@ApiModelProperty(position = 3, required = true)
	@NotBlank(message = "Beer Alcohol Content is required.")
	private String alcoholContent;

	@ApiModelProperty(position = 4, required = true)
	@NotNull(message = "Beer Price is required.")
	@Min(value = 0, message = "Beer Price must be greater than 0.")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	@ApiModelProperty(position = 5, required = true)
	@NotBlank(message = "Beer Category is required.")
	private String category;
}
