package com.project.dao;

import java.util.List;

import com.project.entity.Customer;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;

public interface ProjectDao {
	public void registerCus(Customer customer, String query) throws SomethingWentWrongException;
	public Customer checkValidCus(String query, String loginEmail, String loginPass) throws NoRecordFoundException, SomethingWentWrongException;
	public void registerServiceProv(ServiceProvider serviceProvider, String query) throws SomethingWentWrongException;
	public ServiceProvider checkValidServ(String query, String loginUSerName, String loginPass)
			throws NoRecordFoundException, SomethingWentWrongException;
	 public List<ServiceProvider> viewServiceProviders(String query) throws SomethingWentWrongException;
	 public void addService(Service service) throws SomethingWentWrongException;
	 public void bookAppoinment(ServiceSlot validSlot, ServiceProvider serviceProviders) throws SomethingWentWrongException;



}
