package com.abeldevelop.compile.api.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.abeldevelop.compile.api.resources.Project;
import com.abeldevelop.compile.api.resources.ProjectData;
import com.abeldevelop.compile.core.json.ReadJson;
import com.abeldevelop.compile.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class FindProjectService implements ProjectService {

	private final ReadJson readJson;
	
	@Override
	public List<Project> findAll() {
		List<Project> projectList = new ArrayList<>();
		Map<String, Project> projects = readJson.read();
		for(Map.Entry<String, Project> entry : projects.entrySet()) {
			projectList.add(entry.getValue());
		}
		return projectList;
	}

	@Override
	public List<Project> find(String group, String name, String version) {
		List<Project> projectList = new ArrayList<>();
		Map<String, Project> mavenProjects = readJson.read();
		ProjectData checkData = new ProjectData(group, name, version);
		for(Map.Entry<String, Project> entry : mavenProjects.entrySet()) {
			if(checkInclude(checkData, entry.getValue().getData())) {
				projectList.add(entry.getValue());
			}
		}
		return projectList;
	}
	
	private boolean checkInclude(ProjectData checkData, ProjectData projectDisk) {
		boolean isGroupInclude = true;
		boolean isNameInclude = true;
		boolean isVersionInclude = true;
		
		if(!StringUtils.isEmpty(checkData.getGroup())) {
			if(!projectDisk.getGroup().contains(checkData.getGroup())) {
				isGroupInclude = false;
			}
		}
		if(!StringUtils.isEmpty(checkData.getName())) {
			if(!projectDisk.getName().contains(checkData.getName())) {
				isNameInclude = false;
			}
		}
		if(!StringUtils.isEmpty(checkData.getVersion())) {
			if(!projectDisk.getVersion().contains(checkData.getVersion())) {
				isVersionInclude = false;
			}
		}
		return isGroupInclude && isNameInclude && isVersionInclude;
	}

}
