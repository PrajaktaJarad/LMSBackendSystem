package com.lms.api.dto;

public class TopicRecordsDto {
	
	private Long id;
	private String topic;
	private int numOfQue;
	
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
	public int getNumOfQue() {
		return numOfQue;
	}
	public void setNumOfQue(int numOfQue) {
		this.numOfQue = numOfQue;
	}
	
	

}
