package com.abeldevelop.compile.api.services.project;

import java.util.List;

import com.abeldevelop.compile.api.resources.Project;

public interface ProjectService {

	public List<Project> findAll();
	
	public List<Project> find(String group, String name, String version);
}
