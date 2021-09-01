package com.beerhouse.craftbeer.service.util;

import org.springframework.stereotype.Service;

@Service
public class TextBuilderService {

	public String getExceptionDescriptionForMultiParameters(String entityName, String[] parameters,
			String errorDescription) {
		String exceptionText = "Erro genérico no método " + Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "(";
		for (int i = 0; i < parameters.length; i++) {
			if ((i + 1) < parameters.length) {
				exceptionText = exceptionText.concat(parameters[i] + ", ");
			} else {
				exceptionText = exceptionText.concat(parameters[i]);
			}
		}
		return exceptionText.concat(") da entidade " + entityName.toUpperCase() + ". Erro: " + errorDescription);
	}

	public String getExceptionDescriptionForNotFoundRegister(String entityName, String propertyName,
			String propertyValue) {
		String exceptionText = "Não foi encontrado nenhum registro do tipo " + entityName.toUpperCase()
				+ " com a propriedade " + propertyName.toUpperCase() + " = " + propertyValue.toUpperCase();
		return exceptionText;
	}

	public String getExceptionDescriptionForMultiRegister(String entityName, String propertyName,
			String propertyValue) {
		String exceptionText = "Já existe no sistema um registro de " + entityName.toUpperCase()
				+ " utilizando a propriedade " + propertyName.toUpperCase() + " com o valor de "
				+ propertyValue.toUpperCase();
		return exceptionText;
	}
}
