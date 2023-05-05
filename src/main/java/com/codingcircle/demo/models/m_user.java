package com.codingcircle.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_user")
public class m_user {
	
	@Id
	@Column(name = "id")
	int id;
	
	@Column(name = "userid")
	String userid;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "pawd")
	String pawd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPawd() {
		return pawd;
	}

	public void setPawd(String pawd) {
		this.pawd = pawd;
	}

}
