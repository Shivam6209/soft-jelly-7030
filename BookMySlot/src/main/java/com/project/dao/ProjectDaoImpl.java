package com.project.dao;

import java.util.List;

import javax.swing.border.EtchedBorder;

import com.project.entity.Appoinment;
import com.project.entity.Customer;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
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
		}finally {
			em.close();
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
		} catch (IllegalArgumentException |IllegalStateException e) {
			throw new SomethingWentWrongException("Unable to Login! Please try again");
		}catch (NoResultException e) {
			System.out.println(ANSI_RED+"You Are Not Registerd! Please Register First"+ANSI_RESET);
			}finally {
			em.close();
		}
		return customer;
	}
	@Override
	public void registerServiceProv(ServiceProvider serviceProvider, String query) throws SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		EntityTransaction et= em.getTransaction();
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("reg_email", serviceProvider.getEmail());
			query2.setParameter("reg_username", serviceProvider.getUsername());
			try {
				ServiceProvider serviceProvider1 = (ServiceProvider) query2.getSingleResult();
				System.out.println(ANSI_RED+"You are Already Registerd "+serviceProvider1.getName()+" Please Login"+ANSI_RESET);
			} catch (NoResultException e) {
				et.begin();
				em.persist(serviceProvider);
				et.commit();
			}
		} catch (Exception e) {
			et.rollback();
			throw new SomethingWentWrongException("Unable to Register! Please Try Again");
		}finally {
			em.close();
		}
		
	}
	@Override
	public ServiceProvider checkValidServ(String query, String loginUSerName, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		ServiceProvider serviceProvider = null;
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("login_username", loginUSerName);
			query2.setParameter("login_password", loginPass);
			serviceProvider = (ServiceProvider) query2.getSingleResult();
		} catch (IllegalArgumentException |IllegalStateException e) {
			throw new SomethingWentWrongException("Unable to Login! Please try again");
		}catch (NoResultException e) {
		System.out.println(ANSI_RED+"You Are Not Registerd! Please Register First"+ANSI_RESET);
		}
		finally {
			em.close();
		}
		return serviceProvider;
	}
	@Override
	public List<ServiceProvider> viewServiceProviders(String query) throws SomethingWentWrongException {
		List<ServiceProvider> serviceProviders = null;
		EntityManager em = null;
		try {
			em=DBUtil.getConnection();
			Query createQuery = em.createQuery(query);
			serviceProviders = (List<ServiceProvider>)createQuery.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SomethingWentWrongException("Unable to view Service Providers Details");
		}finally {
			em.close();
		}
		return serviceProviders;
	}
	@Override
	public void addService(Service service) throws SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(service);
			et.commit();
		} catch (Exception e) {
			throw new SomethingWentWrongException("Unable To add Service");
		}finally {
			em.close();
		}
	}
	@Override
	public void bookAppoinment(ServiceSlot validSlot, ServiceProvider serviceProvider) throws SomethingWentWrongException {
	    EntityManager em = null;
	    EntityTransaction et = null;
	    try {
	        em = DBUtil.getConnection();
	        et = em.getTransaction();
	        et.begin();
	        validSlot.setIsAvailabe("no");
	        em.merge(serviceProvider);
	        em.merge(validSlot);
	        et.commit();
	        
	    } catch (Exception e) {
	        if (et != null) {
	            et.rollback();
	        }
	    	e.printStackTrace();
	        System.out.println(e);
	        throw new SomethingWentWrongException("Unable to Book Appointment");
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
	}

}
