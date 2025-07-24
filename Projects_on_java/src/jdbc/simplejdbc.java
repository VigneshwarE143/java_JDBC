package jdbc;

import java.sql.*;

public class simplejdbc {
    public static void main(String[] args) {
        // JDBC variables
        String sql = "SELECT stu_mark FROM tatastrive.student_details WHERE stu_id = 1;";
        String url = "jdbc:mysql://localhost:3306/tatastrive"; // Make sure port 3306 is correct
        String username = "root";  // Replace with your actual username
        String password = "05012005";  // Replace with your actual password

        try {
            // Load the MySQL JDBC driver (optional in modern versions)
        	//-------------optional-----------------//
			/* Class.forName("com.mysql.cj.jdbc.Driver"); */

            // Create connection
            Connection con = DriverManager.getConnection(url, username, password);
            if(con!=null) {
            	System.out.println("connected");
            }

            // Create a statement
            Statement st = con.createStatement();

            // Execute the SQL query
            ResultSet rs = st.executeQuery(sql);

            // Read result
            if (rs.next()) {
                int mark = rs.getInt(1); // Column index 1
                System.out.println("Student Mark: " + mark);
            } else {
                System.out.println("No data found for stu_id = 1");
            }

            // Close connection
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
