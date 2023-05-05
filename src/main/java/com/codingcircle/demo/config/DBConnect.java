package com.codingcircle.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class DBConnect {
	
	public static Session getCurrentSession() {
		  // Hibernate 5.4 SessionFactory example without XML
		  Map<String, String> settings = new HashMap();
		  settings.put("connection.driver_class", "	org.postgresql.Driver");
		  settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		  settings.put("hibernate.connection.url", 
		    "jdbc:postgresql://localhost:5432/postgres");
		  settings.put("hibernate.connection.username", "postgres");
		  settings.put("hibernate.connection.password", "admin");
		  settings.put("hibernate.current_session_context_class", "thread");
		  settings.put("hibernate.show_sql", "true");
		  settings.put("hibernate.format_sql", "true");

		  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		                                    .applySettings(settings).build();

		  MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		  // metadataSources.addAnnotatedClass(Player.class);
		  Metadata metadata = metadataSources.buildMetadata();

		  // here we build the SessionFactory (Hibernate 5.4)
		  SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		  Session session = sessionFactory.getCurrentSession();
		  return session;
		}

}
