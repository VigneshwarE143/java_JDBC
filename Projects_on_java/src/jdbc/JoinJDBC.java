
package jdbc;
import java.sql.*;
class JoinJDBC {
	
public static void main(String[] args) {
	
	String sql="SELECT f.food_id,f.food_name,f.food_price,o.food_name,o.food_count FROM food f left join orders o ON f.food_name=o.food_name";
	String url="jdbc:mysql://localhost:3306/tatastrive";
	String name="root";
	String password="05012005";
	try {
	Connection con =DriverManager.getConnection(url,name,password);
	if(con!=null) {
		System.out.println("conncetion is successfull");
	}
	Statement stmt=con.createStatement();
	ResultSet rs=stmt.executeQuery(sql);
	while(rs.next()) {
		System.out.println("FOOD : "+rs.getString("food_name"));
		System.out.println("Food ID : "+rs.getInt("food_id"));
		System.out.println("Food Price : "+rs.getInt("food_price"));
		System.out.println("Food Order Count : "+rs.getInt("food_count"));
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
	}
	
}catch(Exception e) {
	e.printStackTrace();
}}

}
