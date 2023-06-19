package com.project.service;

import java.util.List;

import com.project.dao.ProjectDao;
import com.project.dao.ProjectDaoImpl;
import com.project.entity.Appoinment;
import com.project.entity.Customer;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;
import com.project.utility.DBUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class ProjectServiceImpl implements ProjectService {
	@Override
	public void registerCustomer(Customer customer, String query) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.registerCus(customer, query);
	}

	@Override
	public Customer checkValidCus(String query, String loginEmail, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		return projectDao.checkValidCus(query, loginEmail, loginPass);
	}

	@Override
	public void registerServiceProv(ServiceProvider serviceProvider, String query) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.registerServiceProv(serviceProvider, query);

	}

	@Override
	public ServiceProvider checkValidServ(String query, String loginUSerName, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		return projectDao.checkValidServ(query, loginUSerName, loginPass);
	}

	@Override
	public List<ServiceProvider> viewServiceProviders(String query) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		return projectDao.viewServiceProviders(query);
	}

	@Override
	public void addService(Service service) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.addService(service);
	}

	@Override
	public ServiceProvider findServiceProvider(String userName) throws NoRecordFoundException {
		ServiceProvider serviceProvider = null;
		EntityManager em = null;
		try {
			em = DBUtil.getConnection();
			serviceProvider = em.find(ServiceProvider.class, userName);
			if (serviceProvider == null) {
				throw new NoRecordFoundException("Service Provider With this UserName Not Available");
			}
		} catch (Exception e) {
			throw new NoRecordFoundException("Service Provider With this UserName Not Available");
		} finally {
			em.close();
		}
		return serviceProvider;
	}

	@Override
	public Service findService(String title) throws NoRecordFoundException {
		EntityManager em = null;
		Service service = null;
		try {
			em = DBUtil.getConnection();
			service = em.find(Service.class, title);
			if (service == null) {
				throw new NoRecordFoundException("Service With Given Title Not Available");
			}
		} catch (Exception e) {
			throw new NoRecordFoundException("Service With Given Title Not Available");
		} finally {
			em.close();
		}
		return service;
	}

	@Override
	public ServiceSlot findValidSlot(int id) throws NoRecordFoundException {
		EntityManager em = null;
		ServiceSlot serviceSlot = null;
		try {
			em = DBUtil.getConnection();
			serviceSlot = em.find(ServiceSlot.class, id);
			if (serviceSlot == null) {
				throw new NoRecordFoundException("Given Service Slot Not Available");
			}
		} catch (Exception e) {
			throw new NoRecordFoundException("Given Service Slot Not Available");
		} finally {
			em.close();
		}
		return serviceSlot;
	}

	@Override
	public void bookAppoinment(ServiceSlot validSlot, ServiceProvider serviceProviders)
			throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.bookAppoinment(validSlot, serviceProviders);

	}

	@Override
	public List<Appoinment> showAppoinment(Customer customer, String query)
			throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;
		List<Appoinment> appoinments = null;
		try {
			em = DBUtil.getConnection();
			Query createQuery = em.createQuery(query);
			createQuery.setParameter("id", customer);
			appoinments = (List<Appoinment>) createQuery.getResultList();
		} catch (Exception e) {
			throw new NoRecordFoundException("Something Went Wrong Please Try Again");
		} finally {
			em.close();
		}
		return appoinments;
	}

	@Override
	public Appoinment findAppoinment(int id) throws NoRecordFoundException {
		EntityManager em = null;
		Appoinment appoinment = null;
		try {
			em = DBUtil.getConnection();
			appoinment = em.find(Appoinment.class, id);
			if (appoinment == null) {
				throw new NoRecordFoundException("AppoinMent With Given Id Not Available!");
			}
		} catch (Exception e) {
			throw new NoRecordFoundException("Something Went Wrong Please Try Again");
		} finally {
			em.close();
		}
		return appoinment;
	}

	@Override
	public void cancelAppoinment(Appoinment appoinment) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.cancelAppoinment(appoinment);	
	}

	@Override
	public List<Appoinment> viewAppoinmentSp(ServiceProvider sp)
			throws SomethingWentWrongException, NoRecordFoundException {
		ProjectDao projectDao = new ProjectDaoImpl();
		return projectDao.viewAppoinmentSp(sp);
	}

	@Override
	public void giveService(Appoinment ap,String status) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.giveService(ap,status);
	}

	@Override
	public List<Service> viewServices(ServiceProvider serviceProvider)throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		return projectDao.viewServices(serviceProvider);
	}

	@Override
	public void addServiceSlot(Service service) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.addServiceSlot(service);
	}
}
