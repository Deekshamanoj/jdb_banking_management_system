package com.ty.bankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Accounts {
	private Connection con;
	private Scanner sc;
	public Accounts(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	//method to check whether account exist or not
		boolean account_exist(String email)
		{
			String sql="select * from accounts where email=?";
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
	
		//method to generate account number 
		long generateAccountNum()
		{
			Statement stm;
			try {
				stm = con.createStatement();
				String sql="select account_number from accounts order by account_number desc limit 1";
				ResultSet rs=stm.executeQuery(sql);
				if(rs.next()) {
					long last_number=rs.getLong(1);
					return last_number+1;
				}
				else
				{
					return 10000100;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 10000100;	
		}
		
		
		//method to get account_number of particular person
		long get_account_number(String email)
		{
			String sql="select account_number from accounts where email=?";
			try {
				PreparedStatement pstm=con.prepareStatement(sql);
				pstm.setString(1, email);
				ResultSet rs=pstm.executeQuery();
				if(rs.next()) {
					return rs.getLong(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("Account number doesnot exist");
		}
		
		//method to open new account
		long open_account(String email) 
		{
			if(!account_exist(email)) {
				String sql="insert into accounts values(?,?,?,?)";
				System.out.println("Full name : ");
				String name = sc.nextLine();
				System.out.println("Initial amount : ");
				double balance = sc.nextDouble();
				System.out.println("Enter pin : ");
				int pin=sc.nextInt();
				long account_number=generateAccountNum();
				PreparedStatement pstm;
				try {
					pstm = con.prepareStatement(sql);
					pstm.setLong(1, account_number);
					pstm.setString(2, name);
					pstm.setString(3, email);
					pstm.setDouble(4, balance);
					pstm.setInt(5, pin);
					int affected_rows=pstm.executeUpdate();
					if(affected_rows>0) {
						return account_number;
					}else {
						throw new RuntimeException("Account creation failed");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
			throw new RuntimeException("Account already exist");
		}
	

}
