package com.codingcircle.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_transaction")
public class t_transaction {
	
	@Id
	@Column(name = "id")
	int id;
	
	@Column(name = "userids")
	String userids;
	
	@Column(name = "useridr")
	String useridr;
	
	@Column(name = "coins")
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

	public String getCoin() {
		return coins;
	}

	public void setCoin(String coin) {
		this.coins = coin;
	}

	
}
