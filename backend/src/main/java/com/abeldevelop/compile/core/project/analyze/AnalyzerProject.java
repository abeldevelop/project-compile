package com.abeldevelop.compile.core.project.analyze;

import java.util.List;
import java.util.Map;

import com.abeldevelop.compile.api.resources.Project;

public interface AnalyzerProject {

	public List<String> analyze(Map<String, Project> projects);
}
