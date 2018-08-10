package com.abeldevelop.compile.api.resources;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description="Identified of project to work with")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class JsonProjectData {

	@ApiModelProperty(notes="Group of the proyect, it is not obligatory to indicate the complete group", example="com.abeldevelop.", required = true, position = 0)
	@NotNull(message = "group can not be null")
	@NotEmpty(message = "group can not be empty")
	private String group;
	
	@ApiModelProperty(notes="Name of the proyect, it is not obligatory to indicate the complete name", example="project-compile", required = false, position = 1)
	private String name;
	
}
