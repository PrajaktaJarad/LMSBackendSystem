package com.lms.api.dto;

import java.util.List;

import com.lms.api.model.Course;

public class LearningTrackDto {

	private long id;
	private String name;
	private List<Course> course;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Course> getCourse() {
		return course;
	}
	public void setCourse(List<Course> course) {
		this.course = course;
	}
	
}