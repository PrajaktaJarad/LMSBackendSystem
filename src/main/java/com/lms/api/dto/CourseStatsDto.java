package com.lms.api.dto;

public class CourseStatsDto {
	
	int numModules;
	int numVideos;
	String contentDuration;
	
	public int getNumModules() {
		return numModules;
	}
	public void setNumModules(int numModules) {
		this.numModules = numModules;
	}
	public int getNumVideos() {
		return numVideos;
	}
	public void setNumVideos(int numVideos) {
		this.numVideos = numVideos;
	}
	public String getContentDuration() {
		return contentDuration;
	}
	public void setContentDuration(String contentDuration) {
		this.contentDuration = contentDuration;
	}
	
	

}
