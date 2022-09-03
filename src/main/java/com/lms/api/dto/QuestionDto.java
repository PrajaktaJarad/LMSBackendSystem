package com.lms.api.dto;

import java.time.LocalDate;
import java.util.List;

import com.lms.api.model.Answer;

public class QuestionDto {

	private Long questionId;
	private String questiontext;
	private String username;
	private LocalDate dateOfPost;
	private int numOfAnswer;
	
	private List<Answer> asnwer;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getDateOfPost() {
		return dateOfPost;
	}

	public void setDateOfPost(LocalDate dateOfPost) {
		this.dateOfPost = dateOfPost;
	}

	public int getNumOfAnswer() {
		return numOfAnswer;
	}

	public void setNumOfAnswer(int numOfAnswer) {
		this.numOfAnswer = numOfAnswer;
	}

	public List<Answer> getAsnwer() {
		return asnwer;
	}

	public void setAsnwer(List<Answer> asnwer) {
		this.asnwer = asnwer;
	}
	
}
