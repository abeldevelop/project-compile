package com.abeldevelop.compile.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
	        .apis(RequestHandlerSelectors.basePackage("com.abeldevelop.compile.api.controllers"))
	        .paths(PathSelectors.any())
//	        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
//			.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
	        .build()
	        .apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Project Compile", 
				"Compile your Java projects automatically", 
				"1.0", 
				"urn:tos",
				getApiContact(), 
				"Apache 2.0", 
				"http://www.apache.org/licenses/LICENSE-2.0", 
				new ArrayList<>()
		);
	}
	
	private Contact getApiContact() {
		return new Contact(
				"Abel Develop", 
				"http://www.abeldevelop.com", 
				"mail@gmail.com"
		);
	}
	
}
