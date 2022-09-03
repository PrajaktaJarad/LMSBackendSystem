package com.lms.api.dto;

import java.time.LocalDate;
import java.util.List;

import com.lms.api.model.Answer;

public class AnswerDto {

	private Long questionId;
	
	private String questionText;
	private String username;
	private LocalDate DateOfPost;
	private int numberOfAnswer;
	
	private List<Answer> answer;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getDateOfPost() {
		return DateOfPost;
	}

	public void setDateOfPost(LocalDate dateOfPost) {
		DateOfPost = dateOfPost;
	}

	public int getNumberOfAnswer() {
		return numberOfAnswer;
	}

	public void setNumberOfAnswer(int numberOfAnswer) {
		this.numberOfAnswer = numberOfAnswer;
	}

	public List<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}
	
}
