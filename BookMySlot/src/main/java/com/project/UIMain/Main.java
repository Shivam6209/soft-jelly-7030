package com.project.UIMain;

import java.util.Scanner;

import com.project.entity.Customer;
import com.project.exception.SomethingWentWrongException;
import com.project.service.ProjectService;
import com.project.service.ProjectServiceImpl;
import com.project.utility.DBUtil;

import jakarta.persistence.EntityManager;

public class Main {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_PURPLE = "\u001B[34m";
	public static final String ANSI_BLUE = "\u001B[35m";
	public static final String ANSI_RED = "\u001B[31m";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EntityManager em = DBUtil.getConnection();
		System.out.println();
		System.out.println(ANSI_GREEN+"*********WELCOME TO BOOKMYSLOT*********"+ANSI_RESET);
		int choice = 0;
		String input;
		do {
		    try {
		        System.out.println(ANSI_PURPLE+"Enter:-1 For Login as Customer");
		        System.out.println("Enter:-2 For Login as Service Provider");
		        System.out.println("Enter:-3 For Register as Customer");
		        System.out.println("Enter:-4 For Register as Service Provider");
		        System.out.println("Enter:-0 For EXIT"+ANSI_RESET);
		        System.out.print(ANSI_YELLOW+"Enter Your Choice:- "+ANSI_RESET);
		        input = sc.nextLine();

		        if (input.isEmpty()) {
		            choice = -1;
		        } else {
		            choice = Integer.parseInt(input);
		        }

		        switch (choice) {
		            case 1 -> customerLogin(sc);
		            case 2 -> ServiceLogin(sc);
		            case 3 -> customerRegister(sc);
		            case 4 -> ServiceRegister(sc);
		            case 0 -> System.out.println(ANSI_GREEN+"Thanks For Using Our Services"+ANSI_RESET);
		            default -> System.out.println(ANSI_RED+"Invalid Choice "+choice+ANSI_RESET);
		        }
		    } catch (NumberFormatException e) {
		        System.out.println(ANSI_RED+"Invalid input. Please enter a valid integer."+ANSI_RESET);
		        choice = -1; 
		    }
		} while (choice != 0);

		em.close();

	}
	
	public static void customerLogin(Scanner sc) {
		System.out.println();
		System.out.println(ANSI_YELLOW+"Hii User! Please Login Using Correct Credentials"+ANSI_RESET);
		String loginEmail;
		String loginPass;
		try {
			System.out.println(ANSI_GREEN+"Enter Your Login Email");
			loginEmail = sc.next();
			System.out.println("Enter Your Login Password");
			loginPass = sc.next();
			
			String query = "SELECT c FROM Customer c WHERE email=:login_email AND password=:login_password";
			ProjectService projectService = new ProjectServiceImpl();
			Customer customer = projectService.checkValidCus(query,loginEmail,loginPass);
			System.out.println();
			System.out.println(ANSI_RESET+ANSI_BLUE+"Welcome " +customer.getName()+" Happy to see u again"+ANSI_RESET);
			System.out.println();
			CustomerMain.customerRole(sc);
		} catch (Exception e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
		
	}
	public static void customerRegister(Scanner sc) {
		System.out.println();
		System.out.println(ANSI_PURPLE+"Welcome To BOOKMYSLOT! Please Register"+ANSI_RESET);
		String regName;
		String regEmail;
		String regPass;
		try {
			sc.nextLine();
			System.out.println(ANSI_GREEN+"Enter Your Full Name");
			regName = sc.nextLine();
			System.out.println("Enter Your Email");
			regEmail = sc.next();
			System.out.println("Enter a Strong Password"+ANSI_RESET);
			regPass = sc.next();
			Customer customer = new Customer(regName, regEmail, regPass);
			String query = "SELECT c FROM Customer c WHERE email=:login_email AND password=:login_password";
			ProjectService projectService = new ProjectServiceImpl();
			projectService.registerCustomer(customer,query);
			System.out.println();
			System.out.println(ANSI_BLUE+"***Welcome To BOOKMYSLOT*** ThankYou For Registration"+ANSI_RESET);
			System.out.println();
		}catch (SomethingWentWrongException e) {
			System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
		}
		
	}
	public static void ServiceLogin(Scanner sc) {
		
	}
	public static void ServiceRegister(Scanner sc) {
		
	}

}
