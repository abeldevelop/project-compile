package com.abeldevelop.compile.core.project.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.core.json.ReadJson;
import com.abeldevelop.compile.model.ProjectScan;
import com.abeldevelop.compile.util.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class DefaultProjectAnalyzer implements AnalyzerProjectService {

	private final ReadJson readJson;
	
	public List<String> analyze(Map<String, Project> projects, ProjectData projectToAnalyze) {
		//TODO => Para que compile
		return null;
//		List<String> errors = new ArrayList<>();
//		errors.addAll(scanProjects(projects, projectToAnalyze));

//		temporal();	//TODO => Borrar esta llama y el metodo
//		return errors;
	}

	private void temporal() {
		List<ProjectScan> projectsAnalyzed = new ArrayList<>();
		
		checkInconsistencies(projectsAnalyzed, pruebaDos().getData(), pruebaTres().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaDos(), pruebaTres()));
		
		checkInconsistencies(projectsAnalyzed, pruebaUno().getData(), pruebaTres().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaUno(), pruebaTres()));
		
		checkInconsistencies(projectsAnalyzed, pruebaCinco().getData(), pruebaTres().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaCinco(), pruebaTres()));
		
		
		
		
		checkInconsistencies(projectsAnalyzed, pruebaUno().getData(), pruebaDos().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaUno(), pruebaDos()));
		
		checkInconsistencies(projectsAnalyzed, pruebaCuatro().getData(), pruebaDos().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaCuatro(), pruebaDos()));
		
		
		
		checkInconsistencies(projectsAnalyzed, pruebaDos().getData(), pruebaUno().getData());
		projectsAnalyzed.add(new ProjectScan(pruebaDos(), pruebaUno()));
		
		
	}
	
	private Project pruebaUno() {
		Project project = new Project();
		project.setData(new ProjectData("com.abeldevelop.prueba", "pruebaUno", "0.0.1-SNAPSHOT"));
		return project;
	}
	
	private Project pruebaDos() {
		Project project = new Project();
		project.setData(new ProjectData("com.abeldevelop.prueba", "pruebaDos", "0.0.1-SNAPSHOT"));
		return project;
	}
	
	private Project pruebaTres() {
		Project project = new Project();
		project.setData(new ProjectData("com.abeldevelop.prueba", "pruebaTres", "0.0.1-SNAPSHOT"));
		return project;
	}
	
	private Project pruebaCuatro() {
		Project project = new Project();
		project.setData(new ProjectData("com.abeldevelop.prueba", "pruebaCuatro", "0.0.1-SNAPSHOT"));
		return project;
	}
	
	private Project pruebaCinco() {
		Project project = new Project();
		project.setData(new ProjectData("com.abeldevelop.prueba", "pruebaCinco", "0.0.1-SNAPSHOT"));
		return project;
	}
	
	
	
	
	
	private List<String> scanProjects(Map<String, Project> projects, ProjectData projectToAnalyze) {
		List<String> errors = new ArrayList<>();
		Map<String, String> projectsAnalyzed = null;
		for(Map.Entry<String, Project> entry : projects.entrySet()) {
			projectsAnalyzed = new HashMap<>();
			Project project = entry.getValue();
			for(Project dependency : project.getDependencies()) {
//				projectsAnalyzed.put(key, value);
			}
		}
		return errors;
	}
	
	private List<String> scanDependencies() {
		List<String> errors = new ArrayList<>();
		
		
		return errors;
	}
	
	
	private void checkInconsistencies(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		checkUnnecessaryDependence(projectsAnalyzed, dependency, project);
		checkCyclicalDependence(projectsAnalyzed, dependency, project);
	}
	
	private void checkUnnecessaryDependence(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		for(ProjectScan projectScan : projectsAnalyzed) {
			if(generateKey(projectScan.getDependency().getData()).equals(generateKey(dependency))) {
				log.error(generateMessageUnnecessaryDependence(dependency, projectScan.getProject().getData(), project));
			}
		}
	}

	private void checkCyclicalDependence(List<ProjectScan> projectsAnalyzed, ProjectData dependency, ProjectData project) {
		for(ProjectScan projectScan : projectsAnalyzed) {
			if(
					generateKey(projectScan.getDependency().getData()).equals(generateKey(project)) &&
					generateKey(projectScan.getProject().getData()).equals(generateKey(dependency))
				) {
				log.error(generateMessageCyclicalDependence(project, dependency));
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
