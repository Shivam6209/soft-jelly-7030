package com.project.service;

import com.project.dao.ProjectDao;
import com.project.dao.ProjectDaoImpl;
import com.project.entity.Customer;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;

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
}
