package com.abeldevelop.compile.core.project.read;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Component;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.core.project.analyze.AnalyzerProjectService;
import com.abeldevelop.compile.exception.ProjectCompileException;
import com.abeldevelop.compile.util.Constants;
import com.abeldevelop.compile.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
public class ReadMavenProject implements ReadProject {

	private final AnalyzerProjectService analyzerProjectService;
	
	@Override
	public void read(Map<String, Project> projects, String directory, List<ProjectData> internalProjects) {
		Model model = retrieveModel(directory);
		fillMavenProject(projects, model, internalProjects, directory);
	}
	
	private Model retrieveModel(String directory) {
		MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
		try {
			model = reader.read(new FileReader(directory + File.separator + Constants.POM_FILE_NAME));
		} catch (IOException | XmlPullParserException e) {
			log.error("Exception: {}", e);
			throw new ProjectCompileException(e.getMessage(), e.getCause());
		}
		return model;
	}
	
	private void fillMavenProject(Map<String, Project> projects, Model model, List<ProjectData> internalProjects, String directory) {
		Project project = retrieveMavenProject(projects, new ProjectData(model.getGroupId(), model.getArtifactId(), model.getVersion()), directory);
		project.setDescription(model.getDescription());
		project.setPackaging(model.getPackaging());
		
		retrieveParent(project, model.getParent());
		retrieveProperties(project, model.getProperties());
		retrieveDependencies(projects, project, model.getDependencies(), internalProjects);
	}
	
	private void retrieveParent(Project project, Parent parent) {
		if(log.isDebugEnabled()) {
			log.debug("Scan if project have parent");
		}
		if(parent != null) {
			project.setParent(new ProjectData(parent.getGroupId(), parent.getArtifactId(), parent.getVersion()));
			if(log.isDebugEnabled()) {
				log.debug("Parent information: {}", project.getParent());
			}
		}
	}
	
	private void retrieveProperties(Project project, Properties properties) {
		if(log.isDebugEnabled()) {
			log.debug("Scan if project have properties");
		}
		if(properties != null && !properties.isEmpty()) {
			for(Map.Entry<Object, Object> entry : properties.entrySet()) {
			    String key = (String) entry.getKey();
			    String value = (String) entry.getValue();
			    project.addProperty(key, value);
			    if(log.isDebugEnabled()) {
			    	log.debug("Propertie found => key: {}, value: {}", key, value);
			    }
			}
		}
	}
	
	private void retrieveDependencies(Map<String, Project> projects, Project project, List<Dependency> dependencies, List<ProjectData> internalProjects) {
		if(log.isDebugEnabled()) {
			log.debug("Scan if project have dependencies");
		}
		if(dependencies != null && !dependencies.isEmpty()) {
			for(Dependency mavenDependency : dependencies) {
				if(isInternalProject(mavenDependency, internalProjects)) {
					addDependency(mavenDependency, projects, project);
				}
				else {
					addExternaDependency(mavenDependency, project);
				}
			}
		}
	}
	
	private void addDependency(Dependency mavenDependency, Map<String, Project> projects, Project project) {
		if(log.isDebugEnabled()) {
			log.debug("Add dependecy as internal");
		}
		String mavenDependencyVersion = retrieveVersion(project.getProperties(), mavenDependency.getVersion());
		Project dependency = retrieveMavenProject(projects, new ProjectData(mavenDependency.getGroupId(), mavenDependency.getArtifactId(), mavenDependencyVersion), null);
		project.addDependency(dependency);
	}
	
	private void addExternaDependency(Dependency mavenDependency, Project project) {
		if(log.isDebugEnabled()) {
			log.debug("Add dependecy as external");
		}
		String mavenDependencyVersion = retrieveVersion(project.getProperties(), mavenDependency.getVersion());
		project.addExternaDependency(new ProjectData(mavenDependency.getGroupId(), mavenDependency.getArtifactId(), mavenDependencyVersion));
	}
	
	private Project retrieveMavenProject(Map<String, Project> projects, ProjectData projectData, String directory) {
		String id = Utils.projectDataToProjectId(projectData);
		if(log.isDebugEnabled()) {
			log.debug("Retrieve the project: {}", id);
		}
		Project project = null;
		if(projects.get(id) == null) {
			if(log.isDebugEnabled()) {
				log.debug("Project is not yet in the map");
			}
			project = new Project();
			project.setData(projectData);
			project.setDirectory(directory);
			
			//TODO => Recoger los errores de analisis
			analyzerProjectService.analyze(projects, projectData);
			
			projects.put(id, project);
		}
		else {
			if(log.isDebugEnabled()) {
				log.debug("Project is in the map");
			}
			project = projects.get(id);
			if(project.getDirectory() == null && directory != null) {
				project.setDirectory(directory);
			}
		}
		return project;
	}
	
	private String retrieveVersion(Map<String, String> properties, String version) {
		if(log.isDebugEnabled()) {
			log.debug("Retrieve version of propertie {}", version);
		}
		if(version == null) {
			return null;
		}
		String result = null;
		if(!version.startsWith("${")) {
			result = version;
		}
		else {
			result = properties.get(version.substring(2, version.length()-1));
		}
		if(log.isDebugEnabled()) {
			log.debug("The version is {}", result);
		}
		return result;
	}
	
	public boolean isThisType(String directory) {
		File f = new File(directory + File.separator + Constants.POM_FILE_NAME);
		return (f.exists() && !f.isDirectory());
	}

	private boolean isInternalProject(Dependency mavenDependency, List<ProjectData> internalProjects) {
		boolean isInternalProject = false;
		ProjectData mavenProjectData = new ProjectData(mavenDependency.getGroupId(), mavenDependency.getArtifactId(), mavenDependency.getVersion());
		String mavenId = Utils.projectDataToProjectId(mavenProjectData);
		if(log.isDebugEnabled()) {
			log.debug("Check is project {}, is internal or external", mavenProjectData);
		}
		for(ProjectData internalProject : internalProjects) {
			String internalId = Utils.projectDataToProjectId(internalProject);
			if(log.isDebugEnabled()) {
				log.debug("Check is project is internal or external");
			}
			if(mavenId.contains(internalId.substring(0, internalId.length() - 1))) {
				isInternalProject = true;
			}
		}
		return isInternalProject;
	}
	
	@Override
	public String getType() {
		return Constants.PROJECT_NAME_MAVEN;
	}
}
