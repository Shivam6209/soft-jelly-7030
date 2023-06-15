package com.project.service;

import com.project.entity.Customer;
import com.project.entity.ServiceProvider;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;

public interface ProjectService {
	public void registerCustomer(Customer customer, String query) throws SomethingWentWrongException;
	public Customer checkValidCus(String query, String loginEmail, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException;
	public void registerServiceProv(ServiceProvider serviceProvider, String query) throws SomethingWentWrongException;
	public ServiceProvider checkValidServ(String query, String loginUSerName, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException;
	

}
