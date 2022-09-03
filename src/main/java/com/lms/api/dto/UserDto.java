package com.lms.api.dto;


import com.lms.api.model.User;


public class UserDto {

	private Long id;
	private String name;
	private String mobile;
	private String email;
	private String city;
	private String username;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserDto convert(User user) {
		UserDto dto = new UserDto();
		dto.setUsername(user.getUsername());
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setCity(user.getCity());
		dto.setEmail(user.getEmail());
		dto.setMobile(user.getMobile());
		return dto;
	}
	
	
}