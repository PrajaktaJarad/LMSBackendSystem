package com.lms.api.dto;

import java.util.List;

import com.lms.api.model.Video;



public class ModulesDto {

    private Long id;
	
	private String name;
	
	private int sequence;
	private List<Video> video;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public List<Video> getVideo() {
		return video;
	}
	public void setVideo(List<Video> video) {
		this.video = video;
	}
	
}
