package com.abeldevelop.compile.core.json;

import java.util.Map;

import com.abeldevelop.compile.api.resources.Project;

public interface SaveJson {

	public boolean save(Map<String, Project> projects);
}
