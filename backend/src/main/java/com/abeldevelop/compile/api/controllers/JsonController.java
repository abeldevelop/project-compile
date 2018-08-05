package com.abeldevelop.compile.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

@Api(tags= {"Json"})
@AllArgsConstructor
@Slf4j
@RestController
public class JsonController implements JsonAPI {

	private final JsonService jsonService;
	
	@ApiOperation(value = "Create a json file with the dependencies of projects in disk")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Create the json file with the dependencies"), 
		@ApiResponse(code = 400, message = "Error in Request"),
		@ApiResponse(code = 500, message = "Internal error")
	})
	@PostMapping("/json")
	@Override
	public ResponseEntity<List<String>> createJson(@Valid @RequestBody JsonData jsonData) {
		if(log.isInfoEnabled()) {
			log.info("Request /json -> {}", jsonData);
		}
		List<String> errors = jsonService.createJson(jsonData);
		return new ResponseEntity<>(errors, HttpStatus.CREATED);
	}
}
