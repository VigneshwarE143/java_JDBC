package jdbc;
import java.sql.*;
import java.util.Scanner;
public class SuperMarket {

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
                System.out.println("1. Insert Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Products");
                System.out.println("4. Delete Products");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: insertProduct(conn, sc); break;
                    case 2: viewProducts(conn); break;
                    case 3: updateProduct(conn, sc); break;
                    case 4: deleteProduct(conn, sc); break;
                    case 5: System.out.println("✅ Connection closed."); return;
                    default: System.out.println("❌ Invalid choice!");
                }
                
            }
            }
       

         catch (SQLException e) {
            e.printStackTrace();
        }

       
        }
        static void insertProduct(Connection conn, Scanner sc) throws SQLException {
            sc.nextLine(); // Clear input buffer in case leftover newline is present

            System.out.print("Enter Product Name: ");
            String name = sc.nextLine(); // Read product name (e.g., "Soap")

            System.out.print("Enter Product Price: ");
            int price = sc.nextInt(); // Read product price (e.g., 15)

            System.out.print("Enter Product Count: ");
            int count = sc.nextInt(); // Read product count (e.g., 10)

            // Prepare the SQL insert query with place holders for name, price, and count
            String insertQuery = "INSERT INTO market (product_name, product_price, product_count) VALUES (?, ?, ?)";

            // Create a prepared statement with the query and ask it to return generated keys (product_entry_id)
            PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name);   // Set product_name = name (1st ?)
            stmt.setInt(2, price);     // Set product_price = price (2nd ?)
            stmt.setInt(3, count);     // Set product_count = count (3rd ?)

            stmt.executeUpdate(); // Execute the INSERT command

            // Fetch the auto-generated product_entry_id from the database
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1); // Get the generated ID
                System.out.println("✅ Product inserted successfully with ID: " + id);
            } else {
                System.out.println("❌ Insertion failed."); // If no key was generated, something went wrong
            }
        }
        static void viewProducts(Connection conn) throws SQLException {
            String sql = "SELECT * FROM market";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Market Inventory ---");
            System.out.printf("%-5s %-15s %-10s %-10s\n", "ID", "Name", "Price", "Count");

            while (rs.next()) {
                int id = rs.getInt("product_entry_id");
                String name = rs.getString("product_name");
                int price = rs.getInt("product_price");
                int count = rs.getInt("product_count");

                System.out.printf("%-5d %-15s %-10d %-10d\n", id, name, price, count);
            }
        }
        static void updateProduct(Connection conn, Scanner sc) throws SQLException {
            System.out.print("Enter Product ID to Update: ");
            int id = sc.nextInt();
            System.out.print("Enter New Price: ");
            int price = sc.nextInt();
            System.out.print("Enter New Count: ");
            int count = sc.nextInt();

            String sql = "UPDATE market SET product_price = ?, product_count = ? WHERE product_entry_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, price);
            ps.setInt(2, count);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();

            System.out.println(rows > 0 ? "✅ Product updated." : "❌ Product not found.");
        }
        static void deleteProduct(Connection conn, Scanner sc) throws SQLException {
            System.out.print("Enter Product ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM market WHERE product_entry_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            System.out.println(rows > 0 ? "✅ Product deleted." : "❌ Product not found.");
        }




    
	

   
    }
   
