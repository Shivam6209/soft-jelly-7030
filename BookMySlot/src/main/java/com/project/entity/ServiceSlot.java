package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String slot_time;
	@Column(columnDefinition = "VARCHAR(10) DEFAULT 'yes'", name = "Available")
	private String isAvailabe = "yes";

	@ManyToOne
	private Service ss;

	public ServiceSlot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceSlot(String slot_time) {
		super();
		this.slot_time = slot_time;
	}

	public String getSlot_time() {
		return slot_time;
	}

	public void setSlot_time(String slot_time) {
		this.slot_time = slot_time;
	}

	public Service getSs() {
		return ss;
	}

	public void setSs(Service ss) {
		this.ss = ss;
	}

	public String getIsAvailabe() {
		return isAvailabe;
	}

	public void setIsAvailabe(String isAvailabe) {
		this.isAvailabe = isAvailabe;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ServiceSlot [slotId= "+id+", slotTime=" + slot_time + ", Availabe=" + isAvailabe;
	}

}
