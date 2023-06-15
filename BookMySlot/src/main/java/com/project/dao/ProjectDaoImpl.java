package com.project.dao;

import com.project.entity.Customer;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;
import com.project.utility.DBUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ProjectDaoImpl implements ProjectDao {
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	

	@Override
	public void registerCus(Customer customer,String query) throws SomethingWentWrongException {
		
		EntityManager em = DBUtil.getConnection();
		EntityTransaction et= em.getTransaction();
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("login_email", customer.getEmail());
			query2.setParameter("login_password", customer.getPassword());
			try {
				Customer customer1 = (Customer) query2.getSingleResult();
				System.out.println(ANSI_RED+"You are Already Registerd "+customer1.getName()+" Please Login"+ANSI_RESET);
			} catch (NoResultException e) {
				et.begin();
				em.persist(customer);
				et.commit();
			}
		} catch (Exception e) {
			et.rollback();
			throw new SomethingWentWrongException("Unable to Register! Please Try Again");
		}
		
	}
	@Override
	public Customer checkValidCus(String query, String loginEmail, String loginPass) throws NoRecordFoundException,SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		Customer customer = null;
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("login_email", loginEmail);
			query2.setParameter("login_password", loginPass);
			customer = (Customer) query2.getSingleResult();
			if(customer==null) {
				throw new NoRecordFoundException("You Are Not Registerd!");
			}
		} catch (IllegalArgumentException | NoResultException |IllegalStateException e) {
			throw new SomethingWentWrongException("Unable to Login! Please try again");
		}finally {
			em.close();
		}
		return customer;
	}

}