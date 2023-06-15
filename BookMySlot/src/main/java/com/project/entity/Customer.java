package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int customer_id;
	private String name;
	private String email;
	private String password;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ "]";
	}
	
	
	
	

}
