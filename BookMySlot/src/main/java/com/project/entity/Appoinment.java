package com.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appoinment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int appoinmentId;
	private String customerName;
	private String serviceName;
	private String slot;
	private int slotID;
	private String status;
	private LocalDateTime booked_at;
	private LocalDateTime response_at;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "serviceproviderUsername")
	private ServiceProvider serviceProvider;

	public Appoinment() {
		super();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSlot() {
		return slot;
	}

	public int getSlotID() {
		return slotID;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public int getAppoinmentId() {
		return appoinmentId;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public int getAppoinment_id() {
		return appoinmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public LocalDateTime getBooked_at() {
		return booked_at;
	}

	public void setBooked_at(LocalDateTime booked_at) {
		this.booked_at = booked_at;
	}

	public LocalDateTime getResponse_at() {
		return response_at;
	}

	public void setResponse_at(LocalDateTime response_at) {
		this.response_at = response_at;
	}

	@Override
	public String toString() {
		return "Appoinment [appoinment_id=" + appoinmentId + ", serviceName="
				+ serviceName + ", slot=" + slot + ", slotId=" + slotID + ", status=" + status;
	}

}
