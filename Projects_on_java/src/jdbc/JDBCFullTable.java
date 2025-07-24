package jdbc;


import java.sql.*;




import java.sql.*;

public class JDBCFullTable {
    public static void main(String[] args) {
        // DB credentials
        String url = "jdbc:mysql://localhost:3306/tatastrive";
        String user = "root";
        String password = "05012005"; // Replace with your MySQL password

        try {
            // Step 1: Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            

            // Step 2: Establish connection
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("✅ Connected to database");
            }

            
            

            // Step 4: Create Statement
            Statement stmt = con.createStatement();

            
            // Step 5: Execute SQL query to retrieve full table
            String query = "SELECT * FROM student_details";
            ResultSet rs = stmt.executeQuery(query);
            
            
            
			
            // Step 6: Process ResultSet
            System.out.println("\n--- Student Details Table ---");
            System.out.println("ID\tNum\t\tDept\tMark");
            while (rs.next()) {
                int id = rs.getInt("stu_id");
                int num = rs.getInt("stu_num");
                String dept = rs.getString("stud_dept");
                int mark = rs.getInt("stu_mark");

                System.out.printf("%d\t%-10d\t%-10s\t%d\n", id, num, dept, mark);
            }

            // Step 7: Close connection
            con.close();
            System.out.println("\n✅ Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




