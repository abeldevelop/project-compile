package com.abeldevelop.compile.api.resources;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Compile {

	@NotNull(message = "projects can not be null")
	@NotEmpty(message = "projects can not be empty")
	@Size(min = 1, message = "Must indicate at least one project")
	private List<ProjectData> projects;
	
	private List<ProjectData> excludesProjects;
	
	private Boolean compileDepencies;
	
	private Boolean onlyFirstLevel;
	
	@NotNull(message = "goals can not be null")
	@NotEmpty(message = "goals can not be empty")
	@Size(min = 1, message = "Must indicate at least one goal")
	private List<String> goals;
	
}
