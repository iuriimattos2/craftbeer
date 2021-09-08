package com.beerhouse.craftbeer.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeerPartialUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	private String name;

	@ApiModelProperty(position = 2)
	private String ingredients;

	@ApiModelProperty(position = 3)
	private String alcoholContent;

	@ApiModelProperty(position = 4)
	@Min(value = 0, message = "Beer Price must be greater than 0.")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	@ApiModelProperty(position = 5)
	private String category;
}
