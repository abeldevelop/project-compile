package com.abeldevelop.compile.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description="Details of project")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Project {

	private String directory;
	private ProjectData data;	
	private String description;
	private String packaging;
	private ProjectData parent;
	private Map<String, String> properties;
	private List<Project> dependencies;
	private List<ProjectData> externaDependencies;
	
	public void addProperty(String key, String value) {
		if(properties == null) {
			properties = new HashMap<>();
		}
		properties.put(key, value);
	}
	
	public void addDependency(Project dependecy) {
		if(dependencies == null) {
			dependencies = new ArrayList<>();
		}
		dependencies.add(dependecy);
	}
	
	public void addExternaDependency(ProjectData projectData) {
		if(externaDependencies == null) {
			externaDependencies = new ArrayList<>();
		}
		externaDependencies.add(projectData);
	}
	
}
