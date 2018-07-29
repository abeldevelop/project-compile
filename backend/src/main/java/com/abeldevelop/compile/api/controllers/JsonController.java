package com.abeldevelop.compile.api.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abeldevelop.compile.api.resources.JsonData;
import com.abeldevelop.compile.api.services.json.JsonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
@Api(
		description="Operation about Json file",
		tags= {"Json"}
		
)
public class JsonController implements JsonAPI {

	private final JsonService jsonService;
	
	@ApiOperation(
			value = "Create a json file with the dependencies of projects in disk", 
			response = Void.class, 
			nickname = "createPaysheet", 
			httpMethod = "POST", 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Create the json file with the dependencies") 
	})
	@PostMapping("/json")
	@Override
	public ResponseEntity<Void> createJson(@Valid @RequestBody JsonData jsonData) {
		if(log.isInfoEnabled()) {
			log.info("Request /json -> {}", jsonData);
		}
		jsonService.createJson(jsonData);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
