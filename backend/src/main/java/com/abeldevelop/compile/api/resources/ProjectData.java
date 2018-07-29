package com.abeldevelop.compile.api.resources;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProjectData {

	@NotNull(message = "group can not be null")
	@NotEmpty(message = "group can not be empty")
	private String group;
	
	@NotNull(message = "name can not be null")
	@NotEmpty(message = "name can not be empty")
	private String name;
	
	@NotNull(message = "version can not be null")
	@NotEmpty(message = "version can not be empty")
	private String version;
	
}
