package com.abeldevelop.compile.util;

import org.apache.commons.lang3.StringUtils;

import com.abeldevelop.compile.api.resources.ProjectData;

public abstract class Utils {

	public static String projectDataToProjectId(ProjectData projectData) {
		StringBuilder builder = new StringBuilder();
		if(!StringUtils.isEmpty(projectData.getGroup())) {
			builder
				.append(projectData.getGroup())
			;
		}
		if(!StringUtils.isEmpty(projectData.getName())) {
			builder
				.append(projectData.getName())
			;
		}
		if(!StringUtils.isEmpty(projectData.getVersion())) {
			builder
				.append(projectData.getVersion())
			;
		}
		return builder.toString();
	}
	
//	public static ProjectData projectDataToProjectId(String projectId) {
//		String[] projectIdArr = projectId.split(Constants.PROJECT_ID_SEPARATOR);
//		return new ProjectData(projectIdArr[0], projectIdArr[0], projectIdArr[0]);
//	}
	
	public static String generateMessageErrorProjectNotInDisk(ProjectData projectData) {
		return new StringBuilder()
				.append("The project with ")
				.append("Group Id: ")
				.append(projectData.getGroup())
				.append(", ")
				.append("Artifact Id: ")
				.append(projectData.getName())
				.append("and ")
				.append("Version: ")
				.append(projectData.getVersion())
				.append(" is not on the disk")
				.toString();
	}
	
	private Utils() {
		
	}
}
