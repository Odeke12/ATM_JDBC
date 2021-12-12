package dbtrial;
import java.sql.*;
import java.util.*;
public class dbhandler {
    public static void main(String[] args) {
        try{
        	
            Class.forName("com.mysql.cj.jdbc.Driver"); //register our driver
            String username="root";
            String password="";
            String url = "jdbc:mysql://localhost/users";
            
 
            Connection con = DriverManager.getConnection(url, username, password);
            
            //Creating table
            Statement stmt = con.createStatement();
//            String sql = "CREATE TABLE students (id int AUTO_INCREMENT primary Key, name VARCHAR(20), regno VARCHAR(40), age int(20))";
//            stmt.execute(sql);
            
            //Inserting records
//            System.out.println("Inserting records into students");
//            String sql1 = "INSERT INTO students VALUES (1,'Odeke Trevor','18/U/21109/PS',23)";
//            stmt.execute(sql1);
//            
//            System.out.println("Records successfully added");
            
            //Retrieving from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while(rs.next()) {
            	//Displaying values
            	System.out.println("ID: "+ rs.getInt("id"));
            	System.out.println("NAME: "+ rs.getString("name"));
            	System.out.println("REG NO: "+ rs.getString("regno"));
            	System.out.println("AGE: "+ rs.getInt("age"));
            }
            
//            Statement stmt = con.createStatement();
//            String query = "DROP TABLE users";
//            stmt.execute(query); 

            // Properties prop = new Properties();
            // prop.put("user",username);
            // prop.put("password",password);
            // Connection con = DriverManager.getConnection(prop);

            System.out.println("Connection established");

        }catch(Exception e){
            System.out.println("Got exception : "+e.getMessage());
        }
    }
}
