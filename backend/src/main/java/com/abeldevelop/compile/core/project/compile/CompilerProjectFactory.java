package com.abeldevelop.compile.core.project.compile;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CompilerProjectFactory {

	//TODO => Esto debe ser una lista del tipo ReadProject
	private final CompilerProject compilerMavenProject;
	
	public CompilerProject getImplementation(String directory) {
		//TODO => Recorrer la lista ReadProject para ver cual es la implementacion
		if(compilerMavenProject.isThisType(directory)) {
			return compilerMavenProject;
		}
		return null;
	}
}
