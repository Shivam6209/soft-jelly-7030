package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceSlot {
   @Id
   private int slot_id;
   private String slot_time;
   
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

public int getSlot_id() {
	return slot_id;
}

@Override
public String toString() {
	return "ServiceSlot [slot_id=" + slot_id + ", slot_time=" + slot_time + ", ss=" + ss + "]";
}


   
   
}
