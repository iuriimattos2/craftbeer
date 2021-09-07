package com.beerhouse.craftbeer.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

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

	private String name;

	private String ingredients;

	private String alcoholContent;

	@Min(value = 0, message = "Beer Price must be greater than 0.")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	private String category;
}
