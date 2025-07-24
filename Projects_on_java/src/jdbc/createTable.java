package jdbc;
import java.sql.*;
public class createTable {
public static void main(String[] args) {
	final String URL = "jdbc:mysql://localhost:3306/";
    final String USER = "root";
    final String PASSWORD = "05012005";
    try {
    	Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
    	String Createdb="CREATE DATABASE IF NOT EXISTS Vicky";
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate(Createdb);
    	System.out.println("DataBase is created!");
//    	con.setCatalog("vicky");//

    	Connection con2=DriverManager.getConnection(URL+"Vicky",USER,PASSWORD);
    	Statement stmt2=con2.createStatement();
    	String CreateTable="CREATE TABLE IF NOT EXISTS vickee_details("
    			+ "Full_name varchar(100),"
    			+ "Age int(10),"
    			+ "dateOf_Birth DATE,"
    			+ "Phone_number BIGINT )";
    	stmt2.executeUpdate(CreateTable);
    	System.out.println("Table is created!");
    }catch(Exception e) {
    	e.printStackTrace();
    }
}
}
