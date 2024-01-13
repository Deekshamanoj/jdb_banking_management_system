package com.ty.bankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
	private Connection con;
	private Scanner sc;
	
	public AccountManager(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	//method to debit money
	void debit_money(long account_number) throws SQLException
	{
		System.out.println("Enter amount : ");
		double amount=sc.nextDouble();
		System.out.println("Enter pin : ");
		int pin=sc.nextInt();
		try {
			con.setAutoCommit(false);
			if(account_number!=0) {
				
				String sql="select * from accounts where account_number=? and  pin=?";
				PreparedStatement pstm=con.prepareStatement(sql);
				pstm.setLong(1, account_number);
				pstm.setInt(2, pin);
				ResultSet rs=pstm.executeQuery();
				if(rs.next()) {
					double balance=rs.getDouble("balance");
					if(amount<=balance) {
						String debit_sql="update accounts set balance=balane-? where account_number=?";
						PreparedStatement pstm1=con.prepareStatement(debit_sql);
						pstm1.setDouble(1, amount);
						pstm1.setLong(2, account_number);
						int affected_rows = pstm1.executeUpdate();
						if(affected_rows>0) {
							System.out.println(amount+"rs debited successfully!!!");
							con.commit();
							//or else for other transaction also we need to commit manually 
							con.setAutoCommit(true);
							return;
						}else {
							System.out.println("Transaction failed!!!");
							con.rollback();
							con.setAutoCommit(true);
						}
							
						
					}else {
						System.out.println("Insufficient balance !!!");
					}
					
				}else {
					System.out.println("Invalid pin !!!");
				}
				
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	} 
	
	//method to credit money 
	void debit_money(long account_number) throws SQLException
	{
		System.out.println("Enter amount : ");
		double amount=sc.nextDouble();
		System.out.println("Enter pin : ");
		int pin=sc.nextInt();
	}
	
}
