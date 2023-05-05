package com.codingcircle.demo.dao;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.codingcircle.demo.bean.coinBean;
import com.codingcircle.demo.bean.paymentBean;
import com.codingcircle.demo.bean.userBean;
import com.codingcircle.demo.config.DBConnect;
import com.codingcircle.demo.models.m_user;
import com.codingcircle.demo.models.t_transaction;

@Repository
@Transactional
public class coinDaoImpl implements coinDao
{
	
	@Autowired
	SessionFactory sessionFactory;
	
	DBConnect DBConnect;
	
	public SessionFactory getSessionfactory()
	{
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public String login(String email,String pawd) {
		Session session=this.sessionFactory.openSession();
		List list=null;
		session.beginTransaction();
		String result="Not Found";
		StringBuilder queryStr=new StringBuilder(0);
		try
		{	
			queryStr.append("select * from m_user where email=:email and pawd=:pawd");
			Query query=session.createSQLQuery(queryStr.toString()).setParameter("email",email).setParameter("pawd",pawd);
			list =  query.getResultList();
			if(list!=null && list.size()>0)
			{
				result="Found";
			}
			session.getTransaction().commit();
		    session.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception at login function");
		}
		return result;
	}

	public String getBalance(String userId) {
		Session session=this.sessionFactory.openSession();
		List list=null;
		session.beginTransaction();
		List<coinBean> responseList=new ArrayList();
		StringBuilder queryStr=new StringBuilder(0);
		try
		{	
			queryStr.append("select * from m_coin where userid=:userid");
			Query query=session.createSQLQuery(queryStr.toString()).setParameter("userid",userId);
			list =  query.getResultList();
			if(list!=null && list.size()>0)
			{
				for(Iterator it=list.iterator();it.hasNext();)
				{
					Object[] obj=(Object[])it.next();
					coinBean testBean=new coinBean();
					testBean.setId(Integer.parseInt(obj[0]+""));
					testBean.setUserid(obj[1]+"");
					testBean.setBalance(obj[2]+"");
					responseList.add(testBean);
				}
			}
			session.getTransaction().commit();
		    session.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception at getBalance function");
		}
		return responseList.get(0).getBalance();
	}

	public String pay(paymentBean paymentBean) {
		String status = "Payment not done";
		try {
			long amount=Long.parseLong(paymentBean.getAmount());
			//1.search for receiver id
			boolean found=false;
			Session session=this.sessionFactory.openSession();
			List list=null;
			session.beginTransaction();
			StringBuilder queryStr=new StringBuilder(0);
			queryStr.append("select id from m_user where userid = :userid ");
			Query query=session.createSQLQuery(queryStr.toString()).setParameter("userid",paymentBean.getReceiverId());
			list =  query.getResultList();
			if(list!=null && list.size()>0)
			{
				found=true;
			}
			
			//2.deduct from sender
			boolean deducted = false;
			if(found)
			{
				list=null;
				String senderBalance="";
				StringBuilder queryStr1=new StringBuilder(0);
				queryStr1.append("select balance from m_coin where userid = :userid ");
				Query query1=session.createSQLQuery(queryStr1.toString()).setParameter("userid",paymentBean.getSenderId());
				list =  query1.getResultList();
				if(list!=null && list.size()>0)
				{
					senderBalance=(String) list.get(0);
				}
				
				StringBuilder queryStr2=new StringBuilder(0);
				if(Long.parseLong(senderBalance)>=Long.parseLong(paymentBean.getAmount()))
				{
					String senderFinalBalance=(Long.parseLong(senderBalance)-Long.parseLong(paymentBean.getAmount()))+"";
					queryStr2.append("update m_coin set balance = :balance where userid = :userid ");
					Query query2=session.createSQLQuery(queryStr2.toString()).setParameter("balance",senderFinalBalance)
																			 .setParameter("userid",paymentBean.getSenderId());
					query2.executeUpdate();
					session.getTransaction().commit();
					session.close();
					deducted = true;
				}
				else
				{
					status="Insufficient balancce";
				}
				
			}
			//3.add to receiver
			boolean added=false;
			if(deducted==true && found==true)
			{
				session=this.sessionFactory.openSession();
				session.beginTransaction();
				list=null;
				String receiverBalance="";
				StringBuilder queryStr1=new StringBuilder(0);
				queryStr1.append("select balance from m_coin where userid = :userid ");
				Query query1=session.createSQLQuery(queryStr1.toString()).setParameter("userid",paymentBean.getReceiverId());
				list =  query1.getResultList();
				if(list!=null && list.size()>0)
				{
					receiverBalance=(String) list.get(0);
				}
				
				StringBuilder queryStr2=new StringBuilder(0);
				String reveiverFinalBalance=(Long.parseLong(receiverBalance)+Long.parseLong(paymentBean.getAmount()))+"";
				queryStr2.append("update m_coin set balance = :balance where userid = :userid ");
				Query query2=session.createSQLQuery(queryStr2.toString()).setParameter("balance",reveiverFinalBalance)
																			 .setParameter("userid",paymentBean.getReceiverId());
				query2.executeUpdate();
				session.getTransaction().commit();
				added = true;
				
				status="Payment done successfully";
			}
			//4.add entry in transaction table
			if(deducted==true && found==true && added==true)
			{
				session=this.sessionFactory.openSession();
				session.beginTransaction();
				t_transaction trans=new t_transaction();
				int id=getMaxIDFromTable("t_transaction");
				trans.setId(id);
				trans.setCoin(paymentBean.getAmount());
				trans.setUserids(paymentBean.getSenderId());
				trans.setUseridr(paymentBean.getReceiverId());
				session.save(trans);
				session.getTransaction().commit();
			}
		}
		catch(Exception e)
		{
			status = "Payment not done,contact your admin";
		}
		return status;
	}

	//add data into m_user and m_coin
	public String createUser(userBean userBean) {
		Session session=this.sessionFactory.openSession();
		String status="Created";
		try 
		{
			session=this.sessionFactory.openSession();
			session.beginTransaction();
			m_user user=new m_user();
			user.setEmail(userBean.getEmail());
			user.setPawd(userBean.getPawd());
			user.setUserid(userBean.getUserid());
			user.setUsername(userBean.getUsername());
			session.save(user);
			session.getTransaction().commit();
		    session.close();
		}
		catch(Exception e)
		{
			status="Not Created";
		}
		return status;
	}

	public int getMaxIDFromTable(String tableName)
	{
		Session session=this.sessionFactory.openSession();
		List list=null;
		int maxNum=0;
		session.beginTransaction();
		StringBuilder queryStr=new StringBuilder(0);
		try
		{	
			queryStr.append("select max(id) from t_transaction ");
			Query query=session.createSQLQuery(queryStr.toString());
			list =  query.getResultList();
			if(list!=null && list.size()>0)
			{
				maxNum=(Integer) list.get(0);
			}
			session.getTransaction().commit();
		    session.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception at crudDaoImpl read function");
		}
		return maxNum+1;
		}
}
