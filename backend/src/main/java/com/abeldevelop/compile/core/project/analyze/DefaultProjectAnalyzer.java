package com.abeldevelop.compile.core.project.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.core.json.ReadJson;
import com.abeldevelop.compile.exception.CyclicalProjectCompileException;
import com.abeldevelop.compile.model.ProjectScan;
import com.abeldevelop.compile.util.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class DefaultProjectAnalyzer implements AnalyzerProject {

	public List<String> analyze(Map<String, Project> projects) {
		List<String> errors = new ArrayList<>();
		for(Iterator<Map.Entry<String, Project>> it = projects.entrySet().iterator(); it.hasNext(); ) {
		    Map.Entry<String, Project> entry = it.next();
	    	log.info(entry.getValue().getData().toString());
	    	scanProject(entry.getValue(), new ArrayList<>(), errors);
		}
		return errors;
	}
	
	private void scanProject(Project project, List<ProjectScan> projectsScan, List<String> errors) {
		if(project.getDependencies() != null) {
			for(Project pro : project.getDependencies()) {
				errors.addAll(checkInconsistencies(projectsScan, pro.getData(), project.getData()));
				projectsScan.add(new ProjectScan(pro, project));
			}
			for(Project pro : project.getDependencies()) {
				scanProject(pro, new ArrayList<>(projectsScan), errors);
			}
		}
	}
	
	private List<String> checkInconsistencies(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		List<String> errors = new ArrayList<>();
		checkCyclicalDependence(projectsAnalyzed, dependency, project);
		errors.addAll(checkUnnecessaryDependence(projectsAnalyzed, dependency, project));
		return errors;
	}
	
	private List<String> checkUnnecessaryDependence(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		List<String> errors = new ArrayList<>();
		for(ProjectScan projectScan : projectsAnalyzed) {
			if(generateKey(projectScan.getDependency().getData()).equals(generateKey(dependency))) {
				errors.add(generateMessageUnnecessaryDependence(dependency, projectScan.getProject().getData(), project));
				log.error(generateMessageUnnecessaryDependence(dependency, projectScan.getProject().getData(), project));
			}
		}
		return errors;
	}

	private void checkCyclicalDependence(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		for(ProjectScan projectScan : projectsAnalyzed) {
			log.debug(projectScan.getDependency().getData().getName() + " <=> " + projectScan.getProject().getData().getName());
			if(generateKey(projectScan.getDependency().getData()).equals(generateKey(project)) && generateKey(projectScan.getProject().getData()).equals(generateKey(dependency))) {
				log.error(generateMessageCyclicalDependence(project, dependency));
				throw new CyclicalProjectCompileException(generateMessageCyclicalDependence(project, dependency), 2);
			}
		}
	}

	private String generateMessageUnnecessaryDependence(ProjectData dependency, ProjectData projectInList, ProjectData actualProject) {
		return new StringBuilder()
				.append("You do not need dependence ")
				.append(projectDataToString(dependency))
				.append(" on project ")
				.append(projectDataToString(projectInList))
				.append(", because you already have it in project ")
				.append(projectDataToString(actualProject))
			.toString();
	}
	
	private String generateMessageCyclicalDependence(ProjectData projectOne, ProjectData projectTwo) {
		return new StringBuilder()
				.append("There is a cyclic dependency between project ")
				.append(projectDataToString(projectOne))
				.append(" and project ")
				.append(projectDataToString(projectTwo))
			.toString();
	}
	
	private String projectDataToString(ProjectData projectData) {
		return new StringBuilder()
				.append(projectData.getGroup())
				.append(" ")
				.append(projectData.getName())
				.append(" ")
				.append(projectData.getVersion())
			.toString();
	}
	
	private String generateKey(ProjectData projectData) {
		return new StringBuilder()
				.append(projectData.getGroup())
				.append(Constants.PROJECT_KEY_SEPARATOR)
				.append(projectData.getName())
				.append(Constants.PROJECT_KEY_SEPARATOR)
				.append(projectData.getVersion())
				.toString();
	}
}
