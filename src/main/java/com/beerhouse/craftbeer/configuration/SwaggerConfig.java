package com.beerhouse.craftbeer.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beerhouse.craftbeer.controller.exception.StandardError;
import com.beerhouse.craftbeer.controller.exception.ValidationError;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final ResponseMessage code200 = simpleMessage(200, "Ok", "");
	private final ResponseMessage code201 = simpleMessage(201, "Created", "");
	private final ResponseMessage code204 = simpleMessage(204, "No Content", "");
	private final ResponseMessage code400 = simpleMessage(400, "Bad Request", "StandardError");
	private final ResponseMessage code404 = simpleMessage(404, "Not Found", "StandardError");
	private final ResponseMessage code405 = simpleMessage(405, "Request method not supported", "StandardError");
	private final ResponseMessage code422 = simpleMessage(422, "Unprocessable Entity", "ValidationError");
	private final ResponseMessage code500 = simpleMessage(500, "Internal Server Error", "StandardError");

	private final TypeResolver typeResolver = new TypeResolver();

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(code200, code400, code404, code405, code500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(code201, code405, code422, code500))
				.globalResponseMessage(RequestMethod.PUT,
						Arrays.asList(code200, code400, code404, code405, code422, code500))
				.globalResponseMessage(RequestMethod.PATCH,
						Arrays.asList(code200, code400, code404, code405, code422, code500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(code204, code400, code404, code405, code500))
				.additionalModels(typeResolver.resolve(StandardError.class),
						typeResolver.resolve(ValidationError.class))
				.select().apis(RequestHandlerSelectors.basePackage("com.beerhouse.craftbeer"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ResponseMessage simpleMessage(int code, String message, String body) {
		if (code == 201) {
			Map<String, Header> map = new HashMap<>();
			map.put("location", new Header("location", "Location of inserted resource", new ModelRef("string")));
			return new ResponseMessageBuilder().code(201).message(message).headersWithDescription(map).build();
		} else {
			return new ResponseMessageBuilder().code(code).message(message).responseModel(new ModelRef(body)).build();
		}
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API Craftbeer - Challenge Sensedia",
				"This project was proposed in the selection process of the company Sensedia. This project is a Rest API developed with Spring Boot and data persistence concepts.",
				"Version 1.0", "",
				new Contact("Heitor Amaral", "https://github.com/HeitorAmaral", "heitor.amaral90@outlook.com"), "", "",
				Collections.emptyList());
	}
}