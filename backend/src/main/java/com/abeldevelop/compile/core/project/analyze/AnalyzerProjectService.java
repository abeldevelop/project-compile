package com.abeldevelop.compile.core.project.analyze;

import java.util.List;
import java.util.Map;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;

public interface AnalyzerProjectService {

	public List<String> analyze(Map<String, Project> projects, ProjectData projectData);
}
