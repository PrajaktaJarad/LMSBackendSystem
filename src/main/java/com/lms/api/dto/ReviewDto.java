package com.lms.api.dto;

import javax.persistence.Column;

import com.lms.api.model.LearningTrack;

public class ReviewDto {

	
	private Long id;
	@Column(length=500)
	private String content;	
	private double rating=0.0;
	private Long userId;
	private String username;
	private String name;
	private String email;
	private Long courseId;
	private String courseName;
	private Long learningTrackId;
	private LearningTrack learningTrack;
	
}
