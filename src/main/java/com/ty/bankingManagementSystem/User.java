package com.ty.bankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	private Connection con;
	private Scanner sc;
	public User(Connection con, Scanner sc) {
		super();
		this.con = con;
		this.sc = sc;
	}

	//method to register a user : 
	void register()
	{
		//taking input from user
		System.out.println("Full Name : ");
		String name =sc.nextLine();
		System.out.println("Email : ");
		String email =sc.nextLine();
		System.out.println("Password : ");
		String password =sc.nextLine();

		//checking whether the user exist or not using email
		if(user_exist(email)) {
			System.out.println("User already exist for this email address !!!");
			//if user exist then below codes should not run
			return ;
		}

		String sql="insert into user values(?,?,?,?)";

		//step 3
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setString(2, email);
			pstm.setString(3, password);

			//step 4 
			int affectedRows=pstm.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Registrationsuccessful  !!!");
			}
			else {
				System.out.println("Registration failed !!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//method to login and return email(String)
	//return email so that once user has logged in throughout the program it will be share with other methods
	//so we can use that email to generate and retrieve account number 
	String login()
	{
		System.out.println("Email : ");
		String email =sc.nextLine();
		System.out.println("Password : ");
		String password =sc.nextLine();

		String sql="select * from user where email=? and password=?";

		try {
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm.setString(1, email);
			pstm.setString(2, password);

			ResultSet rs=pstm.executeQuery();

			if(rs.next())
				return email;
			else
				return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//method to check whether user exist or not
	boolean user_exist(String email)
	{
		String sql="select * from user where email=?";
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, email);
			ResultSet rs=  pstm.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
