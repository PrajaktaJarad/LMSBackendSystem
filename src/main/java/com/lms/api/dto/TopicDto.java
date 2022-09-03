package com.lms.api.dto;

import java.util.List;


public class TopicDto {
	
	private Long id;
	private String topic;
	private List<QuestionDtoNew> question;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public List<QuestionDtoNew> getQuestion() {
		return question;
	}
	public void setQuestion(List<QuestionDtoNew> question) {
		this.question = question;
	}
public static class QuestionDtoNew{
		
		private Long id;
		private String questionText;
		private int likes;
		private String username;
		private int numOfAns;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getQuestionText() {
			return questionText;
		}
		public void setQuestionText(String questionText) {
			this.questionText = questionText;
		}
		public int getLikes() {
			return likes;
		}
		public void setLikes(int likes) {
			this.likes = likes;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getNumOfAns() {
			return numOfAns;
		}
		public void setNumOfAns(int numOfAns) {
			this.numOfAns = numOfAns;
		}
		
	}
	
	}
