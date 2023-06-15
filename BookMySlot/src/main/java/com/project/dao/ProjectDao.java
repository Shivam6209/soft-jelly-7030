package com.project.dao;

import com.project.entity.Customer;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;

public interface ProjectDao {
	public void registerCus(Customer customer, String query) throws SomethingWentWrongException;
	public Customer checkValidCus(String query, String loginEmail, String loginPass) throws NoRecordFoundException, SomethingWentWrongException;

}
