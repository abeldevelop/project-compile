package com.abeldevelop.compile.api.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.abeldevelop.compile.api.resources.Compile;
import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.api.resources.ResultData;
import com.abeldevelop.compile.core.json.ReadJson;
import com.abeldevelop.compile.core.project.compile.CompilerProject;
import com.abeldevelop.compile.core.project.compile.CompilerProjectFactory;
import com.abeldevelop.compile.util.BooleanUtils;
import com.abeldevelop.compile.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class DefaultCompilerProjectService implements CompilerProjectService {

	private final CompilerProjectFactory compilerProjectFactory;
	
	private final ReadJson readJson;
	
	@Override
	public ResultData compileProjects(Compile compile) {
		ResultData resultData = new ResultData();

		Map<String, Project> projects = readJson.read();
		resultData.setErrors(compile(projects, compile));
		return resultData;
	}

	private List<String> compile(Map<String, Project> projects, Compile compileIn) {
		List<String> errors = new ArrayList<>();
		if(log.isDebugEnabled()) {
			log.debug("Trying to compile projects");
		}
		for(ProjectData projectDataIn : compileIn.getProjects()) {
			String projectId = Utils.projectDataToProjectId(projectDataIn);
			Project project = projects.get(projectId);
			if(project == null) {
				errors.add(Utils.generateMessageErrorProjectNotInDisk(projectDataIn));
				log.error("The project {}, in not in disk", projectDataIn);
			}
			else {
				checkProjectsToCompile(projects, project, compileIn);
			}
		}
		return errors;
	}
	
	private List<String> checkProjectsToCompile(Map<String, Project> projects, Project project, Compile compileIn) {
		List<String> errors = new ArrayList<>();
		CompilerProject compiler = compilerProjectFactory.getImplementation(project.getDirectory());
		if(!BooleanUtils.isTrue(compileIn.getCompileDepencies())) {
			errors.addAll(compileProject(compiler, project.getData(), project.getDirectory(), new ArrayList<>()));
		}
		else if(BooleanUtils.isTrue(compileIn.getCompileDepencies()) && BooleanUtils.isTrue(compileIn.getOnlyFirstLevel())) {
			errors.addAll(compileOnlyFirstLevelDependecy(compiler, project, new ArrayList<>(), compileIn));
		}
		else if(BooleanUtils.isTrue(compileIn.getCompileDepencies()) && !BooleanUtils.isTrue(compileIn.getOnlyFirstLevel())) {
			List<ProjectData> compiledProjects = new ArrayList<>();
			errors.addAll(compileAllDependecies(compiler, project, compiledProjects, compileIn));
		}
		return errors;
	}

	private List<String> compileAllDependecies(CompilerProject compiler, Project project, List<ProjectData> compiledProjects, Compile compileIn) {
		List<String> errors = new ArrayList<>();
		if(project.getDependencies() != null && !project.getDependencies().isEmpty()) {
			for(Project dependency : project.getDependencies()) {
				if(!StringUtils.isEmpty(dependency.getDirectory())) {
					if(!excludeProject(dependency.getData(), compileIn)) {
						CompilerProject compilerDependency = compilerProjectFactory.getImplementation(dependency.getDirectory());
						compileAllDependecies(compilerDependency, dependency, compiledProjects, compileIn);
					}
				}
				else {
					errors.add(Utils.generateMessageErrorProjectNotInDisk(dependency.getData()));
				}
			}
		}
		errors.addAll(compileProject(compiler, project.getData(), project.getDirectory(), compiledProjects));
		return errors;
	}

	private List<String> compileOnlyFirstLevelDependecy(CompilerProject compiler, Project project, List<ProjectData> compiledProjects, Compile compileIn) {
		List<String> errors = new ArrayList<>();
		if(project.getDependencies() != null && !project.getDependencies().isEmpty()) {
			for(Project dependency : project.getDependencies()) {
				if(!StringUtils.isEmpty(dependency.getDirectory())) {
					if(!excludeProject(dependency.getData(), compileIn)) {
						CompilerProject compilerDependency = compilerProjectFactory.getImplementation(dependency.getDirectory());
						errors.addAll(compileProject(compilerDependency, dependency.getData(), dependency.getDirectory(), compiledProjects));
					}
				}
				else {
					errors.add(Utils.generateMessageErrorProjectNotInDisk(dependency.getData()));
				}
			}
		}
		errors.addAll(compileProject(compiler, project.getData(), project.getDirectory(), compiledProjects));
		return errors;
	}

	private List<String> compileProject(CompilerProject compiler, ProjectData projectData, String directory, List<ProjectData> compiledProjects) {
		List<String> errors = new ArrayList<>();
		if(compiledProjects.contains(projectData)) {
			return errors;
		}
		else {
			compiledProjects.add(projectData);
		}
		if(log.isInfoEnabled()) {
			log.info("Try to compile {} Project -> {}", compiler.getType(), projectData);
		}
		
		if(StringUtils.isEmpty(directory)) {
			if(log.isWarnEnabled()) {
				log.warn("The project is not in disk");
			}
			errors.add(Utils.generateMessageErrorProjectNotInDisk(projectData));
			return errors;
		}
		//return compiler.compile(directory, compileIn.getGoals());
		return errors;
	}
	
	private boolean excludeProject(ProjectData projectData, Compile compileIn) {
		boolean isExclude = false;
		if(compileIn.getExcludesProjects() != null && !compileIn.getExcludesProjects().isEmpty()) {
			String projectId = Utils.projectDataToProjectId(projectData);
			for(ProjectData exclude : compileIn.getExcludesProjects()) {
				String excludeId = Utils.projectDataToProjectId(exclude);
				if(projectId.equals(excludeId)) {
					isExclude = true;
					break;
				}
			}
		}
		return isExclude;
	}
	
}
