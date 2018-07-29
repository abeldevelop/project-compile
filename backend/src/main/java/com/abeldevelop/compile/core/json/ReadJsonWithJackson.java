package com.abeldevelop.compile.core.json;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.configuration.Arguments;
import com.abeldevelop.compile.exception.ProjectCompileException;
import com.abeldevelop.compile.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
public class ReadJsonWithJackson implements ReadJson {

	private Arguments arguments;
	
	@Override
	public Map<String, Project> read() {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Project> mavenProjects = null;
		try {
			TypeReference<HashMap<String,Project>> typeRef = new TypeReference<HashMap<String,Project>>() {};
			File jsonFile = new File(arguments.getJsonDirectory() + File.separator + Constants.JSON_FILE_NAME);
			mavenProjects = mapper.readValue(jsonFile, typeRef);
		} catch (IOException e) {
			throw new ProjectCompileException(e.getMessage(), e.getCause());
		}
		return mavenProjects;
	}
}
