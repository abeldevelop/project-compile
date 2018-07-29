package com.abeldevelop.compile.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.abeldevelop.compile.api.resources.Compile;
import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ResultData;

public interface ProjectAPI {

	public ResponseEntity<List<Project>> findAll();
	
	public ResponseEntity<List<Project>> find(String group, String name, String version);
	
	public ResponseEntity<ResultData> compile(Compile compile);

}
