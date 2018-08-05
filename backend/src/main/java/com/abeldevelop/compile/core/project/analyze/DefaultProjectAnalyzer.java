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

	private final ReadJson readJson;
	
	public List<String> analyze(Map<String, Project> projects) {
		List<String> errors = new ArrayList<>();
		for(Iterator<Map.Entry<String, Project>> it = projects.entrySet().iterator(); it.hasNext(); ) {
		    Map.Entry<String, Project> entry = it.next();
		    if("com.abeldevelop.pruebapruebaTres0.0.1-SNAPSHOT".equals(entry.getKey())) {
		    	log.debug("Es el proyecto 3");
		    	log.info(entry.getValue().getData().toString());
		    	errors.addAll(scanProject(entry.getValue()));
		    }
		}
		return errors;
	}
	
	private List<String> scanProject(Project project) {
		List<String> errors = new ArrayList<>();
		List<ProjectScan> projectScan = new ArrayList<>();
		if(project.getDependencies() != null) {
			for(Project pro : project.getDependencies()) {
				projectScan.add(new ProjectScan(pro, project));
			}
			
			for(Project pro : project.getDependencies()) {
				errors.addAll(scanDependency(pro, projectScan));
			}
		}
		return errors;
	}

	private List<String> scanDependency(Project project, List<ProjectScan> projectScan) {
		List<String> errors = new ArrayList<>();
		log.info(project.getData().toString());
		List<ProjectScan> projectScanCopy = new ArrayList<>(projectScan);
		if(project.getDependencies() != null) {
			for(Project pro : project.getDependencies()) {
				errors.addAll(checkInconsistencies(projectScanCopy, pro.getData(), project.getData()));
				errors.addAll(scanDependency(pro, projectScanCopy));
			}
		}
		return errors;
	}
	
	private List<String> checkInconsistencies(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		List<String> errors = new ArrayList<>();
		errors.addAll(checkUnnecessaryDependence(projectsAnalyzed, dependency, project));
		checkCyclicalDependence(projectsAnalyzed, dependency, project);
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
