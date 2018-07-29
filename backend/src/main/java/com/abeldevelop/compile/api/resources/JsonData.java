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

@ApiModel(description="Details to be able to generate the Json with the information of the Java projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class JsonData {

	@ApiModelProperty(notes="Listado de proyectos internos en disco")
	@NotNull(message = "internalProjects can not be null")
	@NotEmpty(message = "internalProjects can not be empty")
	@Size(min = 1, message = "Must indicate at least one internalProject")
	private List<ProjectData> internalProjects;
	
	@ApiModelProperty(notes="List of directories where java projects are located")
	@NotNull(message = "projectsDirectories can not be null")
	@NotEmpty(message = "projectsDirectories can not be empty")
	@Size(min = 1, message = "Must indicate at least one directory")
	private List<String> projectsDirectories;
	
	
}
