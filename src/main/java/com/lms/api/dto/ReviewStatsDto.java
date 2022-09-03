package com.lms.api.dto;

import com.lms.api.model.LearningTrack;

public class ReviewStatsDto {
	
	int totalReviews;
	int fivestar;
	int fourstar;
	int threestar;
	int twostar;
	int onestar;
	
	public static class ReviewDto{
		private Long id;
		private String content;	
		private double rating;
		private Long userId;
		private String username;
		private String name;
		private String email;
		private Long courseId;
		private String courseName;
		private Long learningTrackId;
		private String learningTrack;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public double getRating() {
			return rating;
		}
		public void setRating(double rating) {
			this.rating = rating;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Long getCourseId() {
			return courseId;
		}
		public void setCourseId(Long courseId) {
			this.courseId = courseId;
		}
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public Long getLearningTrackId() {
			return learningTrackId;
		}
		public void setLearningTrackId(Long learningTrackId) {
			this.learningTrackId = learningTrackId;
		}
		public String getLearningTrack() {
			return learningTrack;
		}
		public void setLearningTrack(String learningTrack) {
			this.learningTrack = learningTrack;
		}
		
	}
	
	public int getTotalReviews() {
		return totalReviews;
	}
	public void setTotalReviews(int totalReviews) {
		this.totalReviews = totalReviews;
	}
	public int getFivestar() {
		return fivestar;
	}
	public void setFivestar(int fivestar) {
		this.fivestar = fivestar;
	}
	public int getFourstar() {
		return fourstar;
	}
	public void setFourstar(int fourstar) {
		this.fourstar = fourstar;
	}
	public int getThreestar() {
		return threestar;
	}
	public void setThreestar(int threestar) {
		this.threestar = threestar;
	}
	public int getTwostar() {
		return twostar;
	}
	public void setTwostar(int twostar) {
		this.twostar = twostar;
	}
	public int getOnestar() {
		return onestar;
	}
	public void setOnestar(int onestar) {
		this.onestar = onestar;
	}
	
	

}
