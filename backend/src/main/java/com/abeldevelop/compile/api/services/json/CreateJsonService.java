package com.abeldevelop.compile.api.services.json;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import com.abeldevelop.compile.api.resources.JsonData;
import com.abeldevelop.compile.api.resources.JsonProjectData;
import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.api.resources.ResultData;
import com.abeldevelop.compile.core.json.SaveJson;
import com.abeldevelop.compile.core.project.analyze.AnalyzerProject;
import com.abeldevelop.compile.core.project.read.ReadProject;
import com.abeldevelop.compile.core.project.read.ReadProjectFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class CreateJsonService implements JsonService {

	private final ReadProjectFactory readProjectFactory;
	
	private final SaveJson saveJson;
	
	private final AnalyzerProject analyzerProject;
	
	@Override
	public ResultData createJson(JsonData jsonData) {
		ResultData resultData = new ResultData();
		List<String> errors = new ArrayList<>();
		Map<String, Project> projects = new HashMap<>();
		List<String> directories = retrieveAllDirectories(jsonData.getProjectsDirectories());
		
		for(String directory : directories) {
			ReadProject readProject = readProjectFactory.getImplementation(directory);
			if(log.isDebugEnabled()) {
				if(readProject == null) {
					log.debug("Directory:: {}, It's not from a known project type", directory);
				}
				else {
					log.debug("Directory: {} es the type: {}", directory, readProject.getType());
				}
			}
			if(readProject != null) {
				List<ProjectData> projectsData = new ArrayList<>();
				for(JsonProjectData jsonProjectData : jsonData.getInternalProjects()) {
					projectsData.add(new ProjectData(jsonProjectData.getGroup(), jsonProjectData.getName(), null));
				}
				readProject.read(projects, directory, projectsData);
			}
		}
		errors.addAll(analyzerProject.analyze(projects));
		deleteProjectsNotInDisk(projects);
		saveJson.save(projects);
		resultData.setErrors(removeDuplicateErrors(errors));
		return resultData;
	}

	private List<String> removeDuplicateErrors(List<String> errors) {
		return new ArrayList<>(new HashSet<>(errors));
	}
	
	private void deleteProjectsNotInDisk(Map<String, Project> projects) {
		for(Iterator<Map.Entry<String, Project>> it = projects.entrySet().iterator(); it.hasNext(); ) {
		    Map.Entry<String, Project> entry = it.next();
		    if(StringUtils.isEmpty(entry.getValue().getDirectory())) {
		    	if(log.isDebugEnabled()) {
		    		log.debug("The project: {}, is not found on the disk", entry.getValue().getData());
		    	}
		        it.remove();
		    }
		}
	}
	
	private List<String> retrieveAllDirectories(List<String> projectsDirectories) {
		if(log.isDebugEnabled()) {
			log.debug("Scanning directories");
		}
		List<String> directories = new ArrayList<>();
		for(String dir : projectsDirectories) {
			File[] dirs = new File(dir).listFiles(File::isDirectory);
			for(File d: dirs) {
				directories.add(d.getAbsolutePath());
				if(log.isDebugEnabled()) {
					log.debug(d.getAbsolutePath());
				}
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("Total directories scan: {}", directories.size());
		}
		return directories;
	}
	
	
	
}
