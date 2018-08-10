package com.abeldevelop.compile.core.project.read;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ReadProjectFactory {
	
	private final List<ReadProject> readProjects;
	
	public ReadProject getImplementation(String directory) {
		for(ReadProject readProject : readProjects) {
			if(readProject.isThisType(directory)) {
				return readProject;
			}
		}
		return null;
	}
}
