package com.abeldevelop.compile.core.json;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.configuration.Arguments;
import com.abeldevelop.compile.exception.ProjectCompileException;
import com.abeldevelop.compile.util.Constants;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
@Component
public class SaveJsonWithJackson implements SaveJson {

	private final Arguments arguments;
	
	@Override
	public boolean save(Map<String, Project> projects) {
		if(log.isDebugEnabled()) {
			log.debug("Save JSON file");
		}
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
		try {
			writer.writeValue(new File(arguments.getJsonDirectory() + File.separator + Constants.JSON_FILE_NAME), projects);
		} catch (IOException e) {
			log.error("Error at Save Json file e -> ", e);
			throw new ProjectCompileException(e.getMessage(), e.getCause());
		}
		if(log.isDebugEnabled()) {
			log.debug("JSON file Save succesfully");
		}
		return true;
	}

}
