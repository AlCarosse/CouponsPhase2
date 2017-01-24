package com.raviv.coupons.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginDetails {

	private long 	userId;
	private String 	user;
	private String	password;
	

	public LoginDetails() {
		super();
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public LoginDetails(long userId, String user, String password) {
		super();
		this.userId = userId;
		this.user = user;
		this.password = password;
	}

	public LoginDetails(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", user=" + user + ", password=" + password + "]";
	}





}
