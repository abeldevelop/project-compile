package com.abeldevelop.compile.api.resources;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(description="Details for the compilation of the projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Compile {

	@ApiModelProperty(notes="List of projects to compile", required = true, position = 0)
	@NotNull(message = "projects can not be null")
	@NotEmpty(message = "projects can not be empty")
	@Size(min = 1, message = "Must indicate at least one project")
	private List<ProjectData> projects;
	
	@ApiModelProperty(notes="List of projects to exclude in compile", required = false, position = 1)
	private List<ProjectData> excludesProjects;
	
	@ApiModelProperty(notes="Indicate whether you want to compile the dependencies of the indicated projects", required = false, position = 2)
	private Boolean compileDepencies;
	
	@ApiModelProperty(notes="Indicates if you want to compile the dependencies only of the first level of the indicated projects, to mark it as true, it must also be marked as true compileDepencies", required = false, position = 3)
	private Boolean onlyFirstLevel;
	
	@ApiModelProperty(notes="Goals to execute, If it is a maven project it is not necessary to indicate mvn", example="[clean, install]", required = true, position = 4)
	@NotNull(message = "goals can not be null")
	@NotEmpty(message = "goals can not be empty")
	@Size(min = 1, message = "Must indicate at least one goal")
	private List<String> goals;
	
}
