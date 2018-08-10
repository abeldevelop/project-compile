package com.abeldevelop.compile.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(notes="Directory in disk of the proyect", example="C:\\Users\\user\\workspace\\projectOne", position = 0)
	private String directory;
	
	@ApiModelProperty(notes="Identified of the project", position = 1)
	private ProjectData data;
	
	@ApiModelProperty(notes="Description of the project", position = 2)
	private String description;
	
	@ApiModelProperty(notes="Type of packaging of the project", position = 3)
	private String packaging;
	
	@ApiModelProperty(notes="Details of the parent project", position = 4)
	private ProjectData parent;
	
	@ApiModelProperty(notes="List of properties of the project", position = 5)
	private Map<String, String> properties;
	
	@ApiModelProperty(notes="List of internal dependencies of the parent project", position = 6)
	private List<Project> dependencies;
	
	@ApiModelProperty(notes="List of external dependencies of the parent project", position = 7)
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
