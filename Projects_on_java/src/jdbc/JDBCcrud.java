package jdbc;

import java.sql.*;
import java.util.Scanner;

public class JDBCcrud {

    static final String URL = "jdbc:mysql://localhost:3306/tatastrive";
    static final String USER = "root";
    static final String PASSWORD = "05012005";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        //Using user input-- with option 1to5 with switch
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to MySQL.");

            while (true) {
                System.out.println("\n--- Student CRUD Menu ---");
                System.out.println("1. Insert Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student Mark");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: insertStudent(conn, sc); break;
                    case 2: viewStudents(conn); break;
                    case 3: updateStudent(conn, sc); break;
                    case 4: deleteStudent(conn, sc); break;
                    case 5: System.out.println("✅ Connection closed."); return;
                    default: System.out.println("❌ Invalid choice!");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sc.close();
    }

    // Insert Student
    static void insertStudent(Connection conn, Scanner sc) throws SQLException {
        sc.nextLine(); // clear input buffer
        System.out.print("Enter Student Number: ");
        int num = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Marks: ");
        int mark = sc.nextInt();

        // Step 1: Insert into class_details and get generated stu_id
        String classInsert = "INSERT INTO class_details () VALUES ()";
        PreparedStatement classStmt = conn.prepareStatement(classInsert, Statement.RETURN_GENERATED_KEYS);
        classStmt.executeUpdate();
        ResultSet generatedKeys = classStmt.getGeneratedKeys();
        int newStuId = -1;

        if (generatedKeys.next()) {
            newStuId = generatedKeys.getInt(1);
            System.out.println("🆕 Generated stu_id from class_details: " + newStuId);
        } else {
            System.out.println("❌ Failed to get generated stu_id.");
            return;
        }

        // Step 2: Insert into student_details using the new stu_id
        String studentInsert = "INSERT INTO student_details (stu_id, stu_num, stud_dept, stu_mark) VALUES (?, ?, ?, ?)";
        PreparedStatement studentStmt = conn.prepareStatement(studentInsert);
        studentStmt.setInt(1, newStuId);
        studentStmt.setInt(2, num);
        studentStmt.setString(3, dept);
        studentStmt.setInt(4, mark);
        studentStmt.executeUpdate();

        System.out.println("✅ Student inserted successfully with stu_id: " + newStuId);
    }

    
    
    // View Students
    static void viewStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM student_details";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- Student Details ---");
        System.out.printf("%-8s%-12s%-15s%-10s\n", "ID", "Number", "Department", "Marks");
        while (rs.next()) {
            int id = rs.getInt("stu_id");
            int num = rs.getInt("stu_num");
            String dept = rs.getString("stud_dept");
            int mark = rs.getInt("stu_mark");

            System.out.printf("%-8d%-12d%-15s%-10d\n", id, num, dept, mark);
        }
    }
    
    

    // Update Marks
    static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter New Marks: ");
        int mark = sc.nextInt();

        String sql = "UPDATE student_details SET stu_mark = ? WHERE stu_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mark);
        ps.setInt(2, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "✅ Marks updated." : "❌ Student not found.");
    }

     
    
   // Delete Student (from both tables)
    static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to Delete: ");
        int id = sc.nextInt();

        // Delete from student_details first due to FK constraint
        String delStudent = "DELETE FROM student_details WHERE stu_id = ?";
        PreparedStatement ps1 = conn.prepareStatement(delStudent);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        // Then delete from class_details
        String delClass = "DELETE FROM class_details WHERE stu_id = ?";
        PreparedStatement ps2 = conn.prepareStatement(delClass);
        ps2.setInt(1, id);
        int rows = ps2.executeUpdate();

        System.out.println(rows > 0 ? "✅ Student deleted." : "❌ Student not found.");
    }
}
