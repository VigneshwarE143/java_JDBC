package jdbc;
import java.sql.*;
public class StoredProcedure {


	
	    public static void main(String[] args) {
	        String jdbcURL = "jdbc:mysql://localhost:3306/tatastrive";
	        String username = "root";
	        String password = "05012005";

	        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
	            CallableStatement stmt = conn.prepareCall("{CALL insert_product(?, ?, ?, ?)}");

	            stmt.setString(1, "Bread"); 
	            stmt.setInt(2, 15); 
	            stmt.setInt(3, 100);  
	            stmt.setInt(4, 5); // 

	            stmt.execute();
	            System.out.println("Product inserted successfully");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}