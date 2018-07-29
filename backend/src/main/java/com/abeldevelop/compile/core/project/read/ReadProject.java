package com.abeldevelop.compile.core.project.read;

import java.util.List;
import java.util.Map;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;

public interface ReadProject {

	public boolean isThisType(String directory);
	
	public String getType();
	
	public void read(Map<String, Project> mavenProjects, String directory, List<ProjectData> internalProjects);
}
