package com.abeldevelop.compile.core.project.compile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.stereotype.Component;

import com.abeldevelop.compile.exception.ProjectCompileException;
import com.abeldevelop.compile.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CompilerMavenProject implements CompilerProject {

	@Override
	public List<String> compile(String directory, List<String> goals) {
		log.info("GOALS: " + goals);
		List<String> errors = new ArrayList<>();
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(new File(directory + File.separator + Constants.POM_FILE_NAME));
		request.setGoals(goals);
		
		
		Invoker invoker = new DefaultInvoker();
		
		InvocationResult result = null;
		try {
			result = invoker.execute(request);
			
		} catch (MavenInvocationException e) {
			throw new ProjectCompileException(e.getMessage(), e.getCause());
		}
		
		if(result.getExitCode() != 0){
			errors.add(generateMessageErrorBuildFailed(directory));
		}
		
		return errors;
	}

	private String generateMessageErrorBuildFailed(String directory) {
		return new StringBuilder()
				.append("The project in directory ")
				.append(directory)
				.append(" failed at build.")
				.toString();
	}

	@Override
	public boolean isThisType(String directory) {
		File f = new File(directory + File.separator + Constants.POM_FILE_NAME);
		return (f.exists() && !f.isDirectory());
	}

	@Override
	public String getType() {
		return Constants.PROJECT_NAME_MAVEN;
	}
}
