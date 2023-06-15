package com.project.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBUtil {
	static EntityManagerFactory emf;
	static {
		emf=Persistence.createEntityManagerFactory("ProjectU5");
	}
	
	public static EntityManager getConnection() {
		return emf.createEntityManager();
	}
	
	
}
