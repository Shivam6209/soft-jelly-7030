package com.project.UIMain;

import java.util.Scanner;

public class CustomerMain {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_PURPLE = "\u001B[34m";
	public static final String ANSI_BLUE = "\u001B[35m";
	public static final String ANSI_RED = "\u001B[31m";

   public static void customerRole(Scanner sc) {
	   boolean isTrue = true;
	   int choice = 0;
	  do {
		  System.out.println(ANSI_PURPLE+"Enter:-1 For View service provider profiles, and Other Info");
			System.out.println("Enter:-2 For Select a suitable appointment slot");
			System.out.println("Enter:-3 For Book the appointment");
			System.out.println("Enter:-4 For Cancel the appointment");
			System.out.println("Enter:-0 For Logout"+ANSI_RESET);
			System.out.print(ANSI_YELLOW+"Enter Your Choice:- "+ANSI_RESET);
			try {
				choice=sc.nextInt();	
			} catch (Exception e) {
				System.out.println(ANSI_RED+"Wrong Input Format"+ANSI_RESET);
			}
			switch (choice) {
			case 1 -> viewService(sc);
			case 2 -> selectAppoi(sc);
			case 3 -> bookAppoi(sc);
			case 4 -> bookAppoi(sc);
			case 0 -> {
				isTrue = false;
				System.out.println();
				System.out.println(ANSI_GREEN+"Thanks For Using Our Services!"+ANSI_RESET);
				System.out.println();
			}
			default -> System.out.println(ANSI_RED+"Invalid Choice "+choice+ANSI_RESET);
			}
	} while (isTrue); 
   }

private static Object bookAppoi(Scanner sc) {
	// TODO Auto-generated method stub
	return null;
}

private static Object selectAppoi(Scanner sc) {
	// TODO Auto-generated method stub
	return null;
}

private static Object viewService(Scanner sc) {
	// TODO Auto-generated method stub
	return null;
}
}
