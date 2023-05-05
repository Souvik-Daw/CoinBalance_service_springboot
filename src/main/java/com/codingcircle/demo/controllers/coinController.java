package com.codingcircle.demo.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.codingcircle.demo.services.coinService;
import com.codingcircle.demo.bean.paymentBean;
import com.codingcircle.demo.bean.userBean;

@RestController
@RequestMapping("/coin")
public class coinController {
	
	@Autowired
	coinService coinService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody userBean userBean)
	{
		String testBean=coinService.login(userBean.getEmail(),userBean.getPawd());
		Map<String,String> map=new HashMap<String,String>();
		map.put("Status", testBean);
		return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getBalance",method=RequestMethod.POST)
	public ResponseEntity<?> getBalance(@RequestBody userBean userBean)
	{
		String testBean=coinService.getBalance(userBean.getUserid());
		Map<String,String> map=new HashMap<String,String>();
		map.put("Balance", testBean);
		return new ResponseEntity(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pay",method=RequestMethod.POST)
	public ResponseEntity<?> pay(@RequestBody paymentBean paymentBean)
	{
		String testBean=coinService.pay(paymentBean);
		Map<String,String> map=new HashMap<String,String>();
		map.put("Status", testBean);
		return new ResponseEntity(map, HttpStatus.OK);
	}
	
//	@RequestMapping(value="/createUser",method=RequestMethod.POST)
//	public ResponseEntity<?> createUser(@RequestBody userBean userBean)
//	{
//		String testBean=coinService.createUser(userBean);
//		Map<String,String> map=new HashMap<String,String>();
//		map.put("Status", testBean);
//		return new ResponseEntity(map, HttpStatus.OK);
//	}

}
