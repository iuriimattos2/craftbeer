package com.beerhouse.craftbeer.service.util;

import org.springframework.stereotype.Service;

@Service
public class TextBuilderService {

	public String getDescriptionForNotFoundRegister(String entityName, String propertyName, String propertyValue) {
		String exceptionText = "Não foi encontrado nenhum registro do tipo " + entityName.toUpperCase()
				+ " com a propriedade " + propertyName.toUpperCase() + " = " + propertyValue.toUpperCase();
		return exceptionText;
	}

}
