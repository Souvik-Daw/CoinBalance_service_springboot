package com.codingcircle.demo.bean;

import javax.persistence.Column;

public class transactionBean {
	
	int id;
	String userids;
	String useridr;
	String coins;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public String getUseridr() {
		return useridr;
	}
	public void setUseridr(String useridr) {
		this.useridr = useridr;
	}
	public String getCoins() {
		return coins;
	}
	public void setCoins(String coins) {
		this.coins = coins;
	}
	
	

}
