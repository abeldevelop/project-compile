package com.abeldevelop.compile.api.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abeldevelop.compile.api.resources.Compile;
import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ResultData;
import com.abeldevelop.compile.api.services.project.CompilerProjectService;
import com.abeldevelop.compile.api.services.project.ProjectService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
public class ProjectController implements ProjectAPI {

	private final ProjectService projectService;
	
	private final CompilerProjectService compilerProjectService;
	
	@GetMapping("/project")
	@Override
	public ResponseEntity<List<Project>> findAll() {
		List<Project> projects = projectService.findAll();
		
		if(log.isInfoEnabled()) {
			log.info("Response -> {}", projects);
		}
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}
	
	@GetMapping("/project/search")
	@Override
	public ResponseEntity<List<Project>> find(@PathParam("group") String group, @PathParam("name") String name, @PathParam("version") String version) {
		if(log.isInfoEnabled()) {
			log.info("Request -> Group: {}, Name: {}, Version: {}", group, name, version);
		}
		List<Project> projects = projectService.find(group, name, version);
		if(log.isInfoEnabled()) {
			log.info("Response -> {}", projects);
		}
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}
	
	@PostMapping("/project/compile")
	@Override
	public ResponseEntity<ResultData> compile(@RequestBody Compile compile) {
		if(log.isInfoEnabled()) {
			log.info("Request -> {}", compile);
		}
		
		ResultData resultData = compilerProjectService.compileProjects(compile);
		
		if(log.isInfoEnabled()) {
			log.info("Response -> {}", resultData);
		}
		return new ResponseEntity<>(resultData, HttpStatus.OK);
	}
	
}
