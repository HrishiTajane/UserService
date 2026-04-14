package com.userservice.Entities;

import javax.persistence.Entity;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Users {
	
	@Id
	@Column(name="Id")
	private String userId;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="About")
	private String about;

	public String getUserId() {
		return userId;
	}
	
	@Transient
	private List<Rating> rating =new ArrayList<>();

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}
	 
	
	
}
