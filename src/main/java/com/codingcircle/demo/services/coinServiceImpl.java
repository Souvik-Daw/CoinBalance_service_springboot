package com.codingcircle.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingcircle.demo.bean.paymentBean;
import com.codingcircle.demo.bean.userBean;
import com.codingcircle.demo.dao.coinDao;

@Service("coinService")
public class coinServiceImpl implements coinService{
	
	@Autowired
	coinDao coinDao;

	public String login(String email,String pawd) {
		return coinDao.login(email,pawd);
	}

	public String getBalance(String userId) {
		return coinDao.getBalance(userId);
	}

	public String pay(paymentBean paymentBean) {
		return coinDao.pay(paymentBean);
	}

	public String createUser(userBean userBean) {
		return coinDao.createUser(userBean);
	}

}
