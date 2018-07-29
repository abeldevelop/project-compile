package com.abeldevelop.compile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.abeldevelop.compile.api.services.project.DefaultCompilerProjectService;
import com.abeldevelop.compile.configuration.Arguments;
import com.abeldevelop.compile.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProjectCompileApplication {

	@Autowired
	private ApplicationArguments appArgs;
	
	@Autowired
	private Arguments arguments;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectCompileApplication.class, args);
	}
	
	@Bean
	public Arguments getArguments() {
		String directory = null;
		if(appArgs.getOptionValues(Constants.ARGUMENT_JSON_DIRECTORY) == null || appArgs.getOptionValues(Constants.ARGUMENT_JSON_DIRECTORY).get(0) == null) {
			directory = System.getProperty("user.home");
		}
		else {
			directory = appArgs.getOptionValues(Constants.ARGUMENT_JSON_DIRECTORY).get(0);
		}
		if(log.isDebugEnabled()) {
			log.debug("Directory to save/read json file of dependencies: {}", directory);
		}
		arguments.setJsonDirectory(directory);
		return arguments;
	}
}
