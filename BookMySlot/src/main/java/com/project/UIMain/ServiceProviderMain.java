package com.project.UIMain;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.SomethingWentWrongException;
import com.project.service.ProjectService;
import com.project.service.ProjectServiceImpl;

public class ServiceProviderMain {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_PURPLE = "\u001B[34m";
	public static final String ANSI_BLUE = "\u001B[35m";
	public static final String ANSI_RED = "\u001B[31m";

   public static void ServiceProviderRole(Scanner sc, ServiceProvider serviceProvider) {
	   

/*
 Set up availability, define working hours, and specify service descriptions.
View appointment details, including customer information and appointment time.
Open available slots for customers to book appointments.
Log out from the service provider account.
 
 */
	   boolean isTrue = true;
	   int choice = 0;
	  do {
		  System.out.println(ANSI_PURPLE+"Enter:-1 For Add new Services");
			System.out.println("Enter:-2 For View Appoinment Details");
			System.out.println("Enter:-0 For Logout"+ANSI_RESET);
			System.out.print(ANSI_YELLOW+"Enter Your Choice:- "+ANSI_RESET);
			try {
				choice=sc.nextInt();	
			} catch (Exception e) {
				sc.nextLine();
				choice=9;
			}
			switch (choice) {
	       	case 1 -> addService(sc,serviceProvider);
			case 2 -> viewAppoi(sc);
			case 0 -> {
				isTrue = false;
				System.out.println();
				System.out.println(ANSI_GREEN+"Thanks For Using Our Services!"+ANSI_RESET);
				System.out.println();
			}
			default -> System.out.println(ANSI_RED+"Invalid Choice! Please Choose Above Choice Only "+ANSI_RESET);
			}
	} while (isTrue); 
   }

public static void viewAppoi(Scanner sc) {
	
}

public static void addService(Scanner sc, ServiceProvider serviceProvider) {
	sc.nextLine();
	System.out.println("Enter Service Title");
	String serviceTitle= sc.nextLine();
	System.out.println("Enter Service Description");
	String serviceDesc= sc.nextLine();
	Service service = new Service(serviceTitle, serviceDesc);
	System.out.println("How Many Slot You Want to add");
	int slot= sc.nextInt();
	Set<ServiceSlot> serviceSlots= new HashSet<>();
	for(int i=0;i<slot;i++) {
		System.out.println("Enter Slot "+(i+1)+" time");
		String slotName= sc.next();
		ServiceSlot serviceSlot = new ServiceSlot(slotName);
		serviceSlot.setSs(service);
		serviceSlots.add(serviceSlot);
	}
	
	service.setServiceSlots(serviceSlots);
	service.setSp(serviceProvider);
	ProjectService ps= new ProjectServiceImpl();
	try {
		ps.addService(service);
	} catch (SomethingWentWrongException e) {
		System.out.println(e.getMessage());
	}
	
	
	
}

}
