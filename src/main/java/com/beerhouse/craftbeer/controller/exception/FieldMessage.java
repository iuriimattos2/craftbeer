package com.beerhouse.craftbeer.controller.exception;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	private String fieldName;

	@ApiModelProperty(position = 2)
	private String message;
}
