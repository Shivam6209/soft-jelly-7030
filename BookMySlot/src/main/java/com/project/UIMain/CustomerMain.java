package com.project.UIMain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.project.entity.Appoinment;
import com.project.entity.Customer;
import com.project.entity.Service;
import com.project.entity.ServiceProvider;
import com.project.entity.ServiceSlot;
import com.project.exception.NoRecordFoundException;
import com.project.exception.SomethingWentWrongException;
import com.project.service.ProjectService;
import com.project.service.ProjectServiceImpl;

public class CustomerMain {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_PURPLE = "\u001B[34m";
	public static final String ANSI_BLUE = "\u001B[35m";
	public static final String ANSI_RED = "\u001B[31m";
	
	public static int  x =1;

	public static void customerRole(Scanner sc, Customer customer) {
		boolean isTrue = true;
		int choice = 0;
		do {
			System.out.println(ANSI_PURPLE + "Enter:-1 For View service provider profiles, and Other Info");
			System.out.println("Enter:-2 For Book the appointment");
			System.out.println("Enter:-3 For View all appointment");
			System.out.println("Enter:-4 For Cancel the appointment");
			System.out.println("Enter:-5 For Search the appointment by id");
			System.out.println("Enter:-6 For Search service provider by username");
			System.out.println("Enter:-0 For Logout" + ANSI_RESET);
			System.out.print(ANSI_YELLOW + "Enter Your Choice:- " + ANSI_RESET);
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				sc.nextLine();
				choice = 9;
			}
			switch (choice) {
			case 1 -> viewService(sc);
			case 2 -> bookAppoi(sc, customer);
			case 3 -> viewAppoi(sc, customer);
			case 4 -> cancelAppoi(sc, customer);
			case 5 -> searchAppoi(sc, customer);
			case 6 -> searchsp(sc);
			case 0 -> {
				isTrue = false;
				System.out.println();
				System.out.println(ANSI_GREEN + "Thanks For Using Our Services!" + ANSI_RESET);
				System.out.println();
			}
			default -> System.out.println(ANSI_RED + "Invalid Choice! Please Choose Above Choice Only" + ANSI_RESET);
			}
		} while (isTrue);
	}

	public static void searchsp(Scanner sc) {
		System.out.print("Enter Service Provider username which you want to search:- ");
		String username = sc.next();
		ProjectService ps = new ProjectServiceImpl();
		try {
			ServiceProvider sp = ps.findServiceProvider(username);
			System.out.println(sp);
		}  catch (NoRecordFoundException e) {
			System.out.println(e.getMessage());
		}	
	}

	public static void searchAppoi(Scanner sc, Customer customer) {
		System.out.print("Enter Appoinment id which you want to search:- ");
		int id = sc.nextInt();
		ProjectService ps = new ProjectServiceImpl();
		try {
			Appoinment appoinment = ps.findAppoinment(id);
			System.out.println(appoinment);
		} catch (NoRecordFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void viewAppoi(Scanner sc, Customer customer) {
		String query = "SELECT a FROM Appoinment a WHERE a.customer = :id";
		ProjectService projectService = new ProjectServiceImpl();
		try {
			List<Appoinment> showAppoinment = projectService.showAppoinment(customer, query);
			if(showAppoinment.size()==0) {
				throw new NoRecordFoundException(ANSI_RED+"No Appoinment was Booked!"+ANSI_RESET);
			}else {
			showAppoinment.forEach(a -> {
			System.out.print(ANSI_BLUE+a+", Service Provider= "+a.getServiceProvider().getName()+", Response At:- ");
			System.out.print(a.getResponse_at()==null?"No Response":a.getResponse_at());
			System.out.print(" ] \n"+ANSI_RESET);
			});
			}
		}catch (SomethingWentWrongException|NoRecordFoundException e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
	}

	public static void cancelAppoi(Scanner sc, Customer customer) {
		String query = "SELECT a FROM Appoinment a WHERE a.customer = :id";
		ProjectService projectService = new ProjectServiceImpl();
		try {
			List<Appoinment> showAppoinment = projectService.showAppoinment(customer, query);
			if(showAppoinment.size()==0) {
				throw new NoRecordFoundException(ANSI_RED+"No Appoinment was Booked!"+ANSI_RESET);
			}else {
			showAppoinment.forEach(a -> System.out.println(a));
			
			}
			System.out.print(ANSI_YELLOW+"Enter Appoinment ID From above which You Want to Cancel :- ");
			int aId=sc.nextInt();
			Appoinment appoinment = projectService.findAppoinment(aId);
			projectService.cancelAppoinment(appoinment);
			System.out.println(ANSI_RESET+ANSI_BLUE+"Appoinment Cancelled SucessFully!"+ANSI_RESET);
		
		}catch (SomethingWentWrongException|NoRecordFoundException e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
	}

	public static void bookAppoi(Scanner sc, Customer customer) {
		viewService(sc);
		System.out.println(
				ANSI_GREEN + "Enter Service Provider UserName From above in which you want to book appoinment :- ");
		
		String userName = sc.next();
		ProjectService projectService = new ProjectServiceImpl();
		try {
			ServiceProvider sp = projectService.findServiceProvider(userName);
			System.out.println(ANSI_RESET + ANSI_PURPLE + "Which Service You Want in " + sp.getName() + " we have :- "
					+ ANSI_RESET);
			Set<Service> services = sp.getServices();
			services.forEach(s -> System.out.println(ANSI_YELLOW + s + ANSI_RESET));
			System.out.print(ANSI_PURPLE + "Which Service You Want To Book Enter service Title from above:- "+ANSI_RESET);
			sc.nextLine();
			String title = sc.nextLine();
			Service service = projectService.findService(title);
			System.out.println(ANSI_YELLOW+"In Which Slot You Want To Book Appoinment We have :- "+ANSI_RESET);
			Set<ServiceSlot> serviceSlots = service.getServiceSlots();
			serviceSlots.forEach(ss -> System.out.println(ss));
			System.out.println(ANSI_YELLOW+"Enter slot id:- "+ANSI_RESET);
			int slot = sc.nextInt();
			ServiceSlot validSlot = projectService.findValidSlot(slot);
			if (validSlot.getIsAvailabe().equals("yes")) {
				Appoinment appoinment = new Appoinment();
				appoinment.setCustomerName(customer.getName());
				appoinment.setServiceName(service.getS_title());
				appoinment.setSlot(validSlot.getSlot_time());
				appoinment.setStatus("Active");
				appoinment.setSlotID(validSlot.getId());
				appoinment.setBooked_at(LocalDateTime.now());

				List<Appoinment> sappoinments = sp.getAppoinments();
				sappoinments.add(appoinment);
				List<Appoinment> cappoinments = customer.getAppoinments();
				cappoinments.add(appoinment);
				appoinment.setServiceProvider(sp);
				appoinment.setCustomer(customer);
				projectService.bookAppoinment(validSlot, sp);
				System.out.println(ANSI_RESET + ANSI_BLUE + "ThankYou " + customer.getName()
						+ " Your Appoinment Booked SucessFully!" + ANSI_RESET);
			} else {
				throw new NoRecordFoundException(ANSI_RED + "Enterd Slot Is Not Available");
			}
		} catch (NoRecordFoundException | SomethingWentWrongException e) {
			System.out.println(e.getMessage() + ANSI_RESET);
		}
	}

	public static void viewService(Scanner sc) {
		String query = "SELECT s FROM ServiceProvider s";
		ProjectService projectService = new ProjectServiceImpl();
		try {
			List<ServiceProvider> serviceProviders = projectService.viewServiceProviders(query);
			int size = serviceProviders.size();
			
			if (size >= 1) {
				serviceProviders.forEach(s -> System.out.println((x++)+":- "+ANSI_YELLOW + s + ANSI_RESET));
				System.out.println();
			} else {
				System.out.println(ANSI_RED + "Service Provider Not Available" + ANSI_RESET);
				System.out.println();
			}

		} catch (SomethingWentWrongException e) {
			System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
		}
	}
}
