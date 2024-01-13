package com.ty.bankingManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
	private static String url = "jdbc:postgresql://localhost:5432/banking_system";
	private static String username = "postgresql";
	private static String password="root";
	
	public static void main(String[] args) {
		
		try {
			Class.forName("org.postgresql.Driver");
			
			Connection con=DriverManager.getConnection(url,username,password);
			
			Scanner sc=new Scanner(System.in);
			
			//here we pass the same connection object and scanner object to all classes 
			//increaser performance and security
			User user = new User(con, sc);
			Accounts acoounts=new Accounts(con, sc);
			AccountManager accountManager=new AccountManager(con, sc);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
