package com.example.app1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AppUser {
	
	private String firstName;
	private String lastName;
	@Id		//to make email as a private
	private String email;
	private String password;
	private String token;
	private int status;			//primitive int coz default value will be 0
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
