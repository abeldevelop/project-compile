package com.abeldevelop.compile.core.project.compile;

import java.util.List;

public interface CompilerProject {

	public boolean isThisType(String directory);
	
	public List<String> compile(String directory, List<String> goals);
	
	public String getType();
}
