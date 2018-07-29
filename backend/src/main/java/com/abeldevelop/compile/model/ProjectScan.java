package com.abeldevelop.compile.model;

import com.abeldevelop.compile.api.resources.Project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectScan {

	private Project dependency;
	
	private Project project;
}
