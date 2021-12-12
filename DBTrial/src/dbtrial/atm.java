package dbtrial;
import java.sql.*;
import java.util.*;
public class atm {
	// Establish connection
	public static Connection connector() throws Exception{

			Class.forName("com.mysql.cj.jdbc.Driver");	
			String username = "root";
			String password ="";
			String url = "jdbc:mysql://localhost/atm";
			
			Connection con = DriverManager.getConnection(url, username, password);
			
			
			return con;
}
	//Authenticating account and pin
	public static Boolean checkpassword(Statement smt, int account_no, int pin) throws Exception{
		String sql = "SELECT * FROM users WHERE account_no="+account_no+" AND pin="+pin;
//		smt.setString(1,"123456");
		ResultSet rs= smt.executeQuery(sql);
		return rs.first();
	}	
	
	//Withdraw method
	public static void withdraw(Statement smt, int account_no, int amount) throws Exception{
		String sql = "SELECT * FROM users WHERE account_no="+ account_no;
		ResultSet rs = smt.executeQuery(sql);
		while(rs.next()) {
		if(rs.getInt("balance") > 0) {
			if((rs.getInt("balance")-amount) > 0) {
			String sql1 = "UPDATE users SET balance="+(rs.getInt("balance")-amount)+" WHERE account_no="+account_no;
			smt.executeUpdate(sql1);
//			System.out.println(rs1);
			System.out.println("Account debited with UGX "+amount);
			}else if((rs.getInt("balance") == amount)){
				System.out.println("Can not leave your account empty");	
			}else {
				System.out.println("You do not have UGX "+amount+" in your account");
			}
		}else {
			System.out.println("You are unable to withdraw");
		}
	 }

	}
	
	//Deposit method
	public static void deposit(Statement smt, Connection con, int account_no, int amount) throws Exception{
		
		String sql = "SELECT * FROM users WHERE account_no="+ account_no;
		ResultSet rs = smt.executeQuery(sql);
		while(rs.next()) {
	

			String sql1 = "UPDATE users SET balance="+(rs.getInt("balance")+amount)+" WHERE account_no="+account_no;
			smt.executeUpdate(sql1);
			System.out.println("Account credited with UGX "+amount);


	 }
		rs.close();
	}
	//create dummy users
	public static void create_users(Statement smt) throws Exception{
		//	Create table
		String sql1 = "CREATE TABLE users (id int AUTO_INCREMENT primary key, account_no int(20) UNIQUE, pin int(20), balance int(20))";
		smt.execute(sql1);
		
    	String query = "INSERT INTO users (account_no , pin, balance) VALUES(111111, 34323, 25000000),(22222, 1222, 3400000),(344444, 54444, 7800000)";
    	smt.execute(query);
	}
	//Check account balance
	public static void checkBalance(Statement smt,int account_no) throws Exception{
		String sql = "SELECT * FROM users WHERE account_no="+account_no;
		ResultSet rs = smt.executeQuery(sql);
		while(rs.next()) {
			System.out.println("You have UGX "+rs.getInt("balance")+" in your account");
		}
	}
	public static void main(String[] args) {
		try {
		
		Statement stmt = connector().createStatement();
//		create_users(stmt);
//		withdraw(stmt);
		

//		stmt.execute(sql1);
		
		System.out.println("Welcome to your ATM");
		System.out.println("Please enter your pin and account number to continue");
		Scanner input = new Scanner(System.in);
		System.out.println("Account number : ");
		int account_num = input.nextInt();
		System.out.println("Pin : ");
		int pin = input.nextInt();
		Boolean check = true;
		if(checkpassword(stmt, account_num, pin) == true) {
			while(true) {
			System.out.println("Follow the prompt below to select your option");
			System.out.println("1: Check account balance");
			System.out.println("2: Deposit on this account");
			System.out.println("3: Withdraw from your account");
			System.out.println("4: Logout");
			
			int option = input.nextInt();
			if(option == 1) {
				checkBalance(stmt, account_num);
			}else if(option == 2) {
				System.out.println("Enter amount you would like to deposit ");
				int deposit  = input.nextInt();
				deposit(stmt,connector(),account_num,deposit);
			}else if(option == 3) {
				System.out.println("Enter amount you would like to withdraw ");
				int amount  = input.nextInt();
				withdraw(stmt,account_num, amount);
				
			}else if(option == 4) {
				System.out.println("See you next time");
				break;
			}
			else if(option != 1 || option != 2 || option != 3 || option != 4) {
				System.out.println("Please pick one of the options");
			}
		 }
		}
		else {
			System.out.println("Wrong credentials");
		}


//		System.out.println("Connection established");
		}catch(Exception e) {
			System.out.println("Got exception : "+e.getLocalizedMessage() +e.getStackTrace());
		}
		
	}
}
