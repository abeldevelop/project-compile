package com.abeldevelop.compile.core.project.read;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ReadProjectFactory {

	//TODO => Esto debe ser una lista del tipo ReadProject
	private final ReadMavenProject readMavenProject;
	
	public ReadProject getImplementation(String directory) {
		//TODO => Recorrer la lista ReadProject para ver cual es la implementacion
		if(readMavenProject.isThisType(directory)) {
			return readMavenProject;
		}
		return null;
	}
}
