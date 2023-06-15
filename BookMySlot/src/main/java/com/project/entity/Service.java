package com.project.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int s_id;
	private String s_title;
	private String s_desc;
	
	@ManyToOne
	private ServiceProvider sp;
	
	@OneToMany(mappedBy = "ss",cascade = CascadeType.ALL)
	private Set<ServiceSlot> serviceSlots  = new HashSet<>();

	public Service() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Service(String s_title, String s_desc) {
		super();
		this.s_title = s_title;
		this.s_desc = s_desc;
	}

	public String getS_title() {
		return s_title;
	}

	public void setS_title(String s_title) {
		this.s_title = s_title;
	}

	public String getS_desc() {
		return s_desc;
	}

	public void setS_desc(String s_desc) {
		this.s_desc = s_desc;
	}

	public ServiceProvider getSp() {
		return sp;
	}

	public void setSp(ServiceProvider sp) {
		this.sp = sp;
	}

	public int getS_id() {
		return s_id;
	}

	@Override
	public String toString() {
		return "Service [s_id=" + s_id + ", s_title=" + s_title + ", s_desc=" + s_desc + ", sp=" + sp + "]";
	}
	
	
	
	
	
	

}
