package com.project.UIMain;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.project.entity.Appoinment;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.NoRecordFoundException;
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
		 * Set up availability, define working hours, and specify service descriptions.
		 * View appointment details, including customer information and appointment
		 * time. Open available slots for customers to book appointments. Log out from
		 * the service provider account.
		 * 
		 */
		boolean isTrue = true;
		int choice = 0;
		do {
			System.out.println(ANSI_PURPLE + "Enter:-1 For Add new Services");
			System.out.println("Enter:-2 For View Appoinment Details");
			System.out.println("Enter:-3 For Give Service to Booked Appoinment");
			System.out.println("Enter:-4 For Open More Slots in Existing Service");
			System.out.println("Enter:-0 For Logout" + ANSI_RESET);
			System.out.print(ANSI_YELLOW + "Enter Your Choice:- " + ANSI_RESET);
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				sc.nextLine();
				choice = 9;
			}
			switch (choice) {
			case 1 -> addService(sc, serviceProvider);
			case 2 -> viewAppoi(sc, serviceProvider);
			case 3 -> giveService(sc, serviceProvider);
			case 4 -> openSlot(sc, serviceProvider);
			case 0 -> {
				isTrue = false;
				System.out.println();
				System.out.println(ANSI_GREEN + "Thanks Admin For Using Our Services!" + ANSI_RESET);
				System.out.println();
			}
			default -> System.out.println(ANSI_RED + "Invalid Choice! Please Choose Above Choice Only " + ANSI_RESET);
			}
		} while (isTrue);
	}

	public static void openSlot(Scanner sc, ServiceProvider serviceProvider) {
		ProjectService ps = new ProjectServiceImpl();
		try {
			List<Service> services = ps.viewServices(serviceProvider);
			services.forEach(s->System.out.println(s));
			System.out.println(ANSI_YELLOW+"Enter Service Tiltle From Above In Which You Want to Add Slot :- "+ANSI_RESET);
			sc.nextLine();
			String title= sc.nextLine();
			Service service = ps.findService(title);
			if(service!=null) {
		      System.out.print(ANSI_RED+"How Many New Slot You Want to add In this Service:- "+ANSI_RESET);
		      int newSlot = sc.nextInt();
		      Set<ServiceSlot> serviceSlots = service.getServiceSlots();
		      for(int i=0;i<newSlot;i++) {
		    	  System.out.println(ANSI_GREEN+"Enter Slot "+(i+1)+" time(hh -> hh):- "+ANSI_RESET);
		    	  String slotTime= sc.next();
		    	  ServiceSlot slot = new ServiceSlot(slotTime);
		    	  serviceSlots.add(slot);
		    	  slot.setSs(service);
		      }
		      service.setServiceSlots(serviceSlots);
		      ps.addServiceSlot(service);
		      System.out.println(ANSI_BLUE+"Slot Updated SucessFully!"+ANSI_RESET);
			}else {
				throw new NoRecordFoundException("NO Service Available With Given Id");
			}
		} catch (Exception e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
	}

	public static void giveService(Scanner sc, ServiceProvider sp) {
		viewAppoi(sc, sp);
		System.out.print(ANSI_BLUE + "Enter Appoinment id from above in which you want to give Service:- ");
		int aId = sc.nextInt();
		ProjectService ps = new ProjectServiceImpl();
		try {
			Appoinment appoinment = ps.findAppoinment(aId);
			System.out.print("Enter Status You Want To Give (Processing/completed):- " + ANSI_RESET);
			String status = sc.next();
			ps.giveService(appoinment, status);
			System.out.println(ANSI_GREEN + "Appoinment Updated SucessFullly!" + ANSI_RESET);
		} catch (NoRecordFoundException | SomethingWentWrongException e) {
			System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
		}

	}

	public static void viewAppoi(Scanner sc, ServiceProvider sp) {
		ProjectService ps = new ProjectServiceImpl();
		try {
			List<Appoinment> appoinmentSp = ps.viewAppoinmentSp(sp);
			appoinmentSp.forEach(a -> System.out.println(ANSI_BLUE + a +", Customer= "+a.getCustomerName()+", Booked At:- "+a.getBooked_at()+" ]"+ ANSI_RESET));
			System.out.println();
		} catch (SomethingWentWrongException | NoRecordFoundException e) {
			System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
		}
	}

	public static void addService(Scanner sc, ServiceProvider serviceProvider) {
		sc.nextLine();
		try {
			System.out.println(ANSI_BLUE+"Enter Service Title");
			String serviceTitle = sc.nextLine();
			System.out.println("Enter Service Description");
			String serviceDesc = sc.nextLine();
			Service service = new Service(serviceTitle, serviceDesc);
			System.out.println("How Many Slot You Want to add");
			int slot = sc.nextInt();
			Set<ServiceSlot> serviceSlots = new HashSet<>();
			for (int i = 0; i < slot; i++) {
				System.out.println("Enter Slot " + (i + 1) + " time(hh-mm)");
				String slotName = sc.next();
				ServiceSlot serviceSlot = new ServiceSlot(slotName);
				serviceSlot.setSs(service);
				serviceSlots.add(serviceSlot);
			}
			service.setServiceSlots(serviceSlots);
			service.setSp(serviceProvider);
			ProjectService ps = new ProjectServiceImpl();
			ps.addService(service);
			System.out.println(ANSI_RESET+ANSI_PURPLE+"New Service Added SucessFully"+ANSI_RESET);
		} catch (Exception e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
	}

}
