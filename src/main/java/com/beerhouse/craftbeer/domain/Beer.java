package com.beerhouse.craftbeer.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Beer implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(position = 2)
	@Column(nullable = false)
	private String name;

	@ApiModelProperty(position = 3)
	@Column(nullable = false)
	private String ingredients;

	@ApiModelProperty(position = 4)
	@Column(nullable = false)
	private String alcoholContent;

	@ApiModelProperty(position = 5)
	@Column(nullable = false)
	private BigDecimal price;

	@ApiModelProperty(position = 6)
	@Column(nullable = false)
	private String category;

	public Beer(String name, String ingredients, String alcoholContent, BigDecimal price, String category) {
		this.name = name;
		this.ingredients = ingredients;
		this.alcoholContent = alcoholContent;
		this.price = price;
		this.category = category;
	}
}
