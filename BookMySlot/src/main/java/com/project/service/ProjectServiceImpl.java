package com.project.service;

import java.util.List;

import com.project.dao.ProjectDao;
import com.project.dao.ProjectDaoImpl;
import com.project.entity.Customer;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;
import com.project.utility.DBUtil;

import jakarta.persistence.EntityManager;

public class ProjectServiceImpl implements ProjectService {
	@Override
	public void registerCustomer(Customer customer,String query) throws SomethingWentWrongException {
		ProjectDao projectDao = new ProjectDaoImpl();
		projectDao.registerCus(customer,query);
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
				if(serviceProvider==null) {
					throw new NoRecordFoundException("Service Provider With this UserName Not Available");
				}
			} catch (Exception e) {
				throw new NoRecordFoundException("Service Provider With this UserName Not Available");
			}finally {
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
				 if(service==null) {
					 throw new NoRecordFoundException("Service With Given Title Not Available");
				 }
			} catch (Exception e) {
				throw new NoRecordFoundException("Service With Given Title Not Available");
			}finally {
				em.close();
			}
			return service;
	}

	@Override
	public ServiceSlot findValidSlot(String slot) throws NoRecordFoundException {
		EntityManager em = null;
		ServiceSlot serviceSlot=null;
		try {
			em = DBUtil.getConnection();
			serviceSlot = em.find(ServiceSlot.class, slot);
			if(serviceSlot==null) {
				throw new NoRecordFoundException("Given Service Slot Not Available");
			}
		} catch (Exception e) {
			throw new NoRecordFoundException("Given Service Slot Not Available");
		}finally {
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
}
