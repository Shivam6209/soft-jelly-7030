package com.project.dao;

import java.time.LocalDateTime;
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
	public void registerCus(Customer customer, String query) throws SomethingWentWrongException {

		EntityManager em = DBUtil.getConnection();
		EntityTransaction et = em.getTransaction();
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("login_email", customer.getEmail());
			query2.setParameter("login_password", customer.getPassword());
			try {
				Customer customer1 = (Customer) query2.getSingleResult();
				System.out.println(
						ANSI_RED + "You are Already Registerd " + customer1.getName() + " Please Login" + ANSI_RESET);
			} catch (NoResultException e) {
				et.begin();
				em.persist(customer);
				et.commit();
			}
		} catch (Exception e) {
			et.rollback();
			throw new SomethingWentWrongException("Unable to Register! Please Try Again");
		} finally {
			em.close();
		}

	}

	@Override
	public Customer checkValidCus(String query, String loginEmail, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		Customer customer = null;
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("login_email", loginEmail);
			query2.setParameter("login_password", loginPass);
			customer = (Customer) query2.getSingleResult();
		} catch (IllegalArgumentException | IllegalStateException e) {
			throw new SomethingWentWrongException("Unable to Login! Please try again");
		} catch (NoResultException e) {
			System.out.println(ANSI_RED + "You Are Not Registerd! Please Register First" + ANSI_RESET);
		} finally {
			em.close();
		}
		return customer;
	}

	@Override
	public void registerServiceProv(ServiceProvider serviceProvider, String query) throws SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		EntityTransaction et = em.getTransaction();
		try {
			Query query2 = em.createQuery(query);
			query2.setParameter("reg_email", serviceProvider.getEmail());
			query2.setParameter("reg_username", serviceProvider.getUsername());
			try {
				ServiceProvider serviceProvider1 = (ServiceProvider) query2.getSingleResult();
				System.out.println(ANSI_RED + "You are Already Registerd " + serviceProvider1.getName()
						+ " Please Login" + ANSI_RESET);
			} catch (NoResultException e) {
				et.begin();
				em.persist(serviceProvider);
				et.commit();
			}
		} catch (Exception e) {
			et.rollback();
			throw new SomethingWentWrongException("Unable to Register! Please Try Again");
		} finally {
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
		} catch (IllegalArgumentException | IllegalStateException e) {
			throw new SomethingWentWrongException("Unable to Login! Please try again");
		} catch (NoResultException e) {
			System.out.println(ANSI_RED + "You Are Not Registerd! Please Register First" + ANSI_RESET);
		} finally {
			em.close();
		}
		return serviceProvider;
	}

	@Override
	public List<ServiceProvider> viewServiceProviders(String query) throws SomethingWentWrongException {
		List<ServiceProvider> serviceProviders = null;
		EntityManager em = null;
		try {
			em = DBUtil.getConnection();
			Query createQuery = em.createQuery(query);
			serviceProviders = (List<ServiceProvider>) createQuery.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SomethingWentWrongException("Unable to view Service Providers Details");
		} finally {
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
		} finally {
			em.close();
		}
	}

	@Override
	public void bookAppoinment(ServiceSlot validSlot, ServiceProvider serviceProvider)
			throws SomethingWentWrongException {
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

	@Override
	public void cancelAppoinment(Appoinment appoinment) throws SomethingWentWrongException {
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = DBUtil.getConnection();
			et = em.getTransaction();
			et.begin();
			int slot_id = appoinment.getSlotID();
			ServiceSlot serviceSlot = em.find(ServiceSlot.class, slot_id);
			serviceSlot.setIsAvailabe("yes");
			em.merge(serviceSlot);
			String query= "DELETE FROM Appoinment a WHERE a.appoinmentId=:id1";
			Query query2 = em.createQuery(query);
			query2.setParameter("id1", appoinment.getAppoinment_id());
			query2.executeUpdate();
			et.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SomethingWentWrongException("Unable to Cancel Appoinment");
		}finally {
			em.close();
		}
	}

	@Override
	public List<Appoinment> viewAppoinmentSp(ServiceProvider sp)
			throws SomethingWentWrongException, NoRecordFoundException {
		String query="SELECT a FROM Appoinment a WHERE serviceProvider = :sp";
		EntityManager em = null;
		EntityTransaction et = null;
		List<Appoinment> appoinments = null;
		try {
			em = DBUtil.getConnection();
			et = em.getTransaction();
			et.begin();
			Query query2 = em.createQuery(query);
			query2.setParameter("sp", sp);
			appoinments=(List<Appoinment>) query2.getResultList();
			if(appoinments.size()==0) {
				throw new NoRecordFoundException("No Appoinment Recevied");
			}
		}catch (Exception e) {
			throw new SomethingWentWrongException(e.getMessage());
		}
		return appoinments;
	}

	@Override
	public void giveService(Appoinment ap,String status) throws SomethingWentWrongException {
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = DBUtil.getConnection();
			et = em.getTransaction();
			et.begin();
			ap.setStatus(status);
			ap.setResponse_at(LocalDateTime.now());
			em.merge(ap);
			et.commit();
		} catch (Exception e) {
			throw new SomethingWentWrongException("Unable to Updated Status");
		}
		
	}

	@Override
	public List<Service> viewServices(ServiceProvider serviceProvider) throws SomethingWentWrongException {
		String query="SELECT s FROM Service s WHERE s.sp =:sProvider";
		EntityManager em = null;
		EntityTransaction et = null;
		List<Service> services =null;
		try {
			em = DBUtil.getConnection();
			et = em.getTransaction();
			Query createQuery = em.createQuery(query);
			createQuery.setParameter("sProvider", serviceProvider);
			services =(List<Service>)createQuery.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SomethingWentWrongException("Unable to Get Services!");
		}
		return services;
	}

	@Override
	public void addServiceSlot(Service service) throws SomethingWentWrongException {
		EntityManager em = DBUtil.getConnection();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(service);
			et.commit();
		} catch (Exception e) {
			throw new SomethingWentWrongException("Unable To add Service");
		} finally {
			em.close();
		}
	}

}
