package com.abeldevelop.compile.core.project.compile;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CompilerProjectFactory {

	private final List<CompilerProject> compilerProjects;
	
	public CompilerProject getImplementation(String directory) {
		
		for(CompilerProject compiler : compilerProjects) {
			if(compiler.isThisType(directory)) {
				return compiler;
			}
		}
		return null;
	}
}
