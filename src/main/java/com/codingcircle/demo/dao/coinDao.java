package com.codingcircle.demo.dao;

import com.codingcircle.demo.bean.paymentBean;
import com.codingcircle.demo.bean.userBean;

public interface coinDao {
	
	public String login(String email,String pawd);
	
	public String getBalance(String userId);
	
	public String pay(paymentBean paymentBean);
	
	public String createUser(userBean userBean);

}
