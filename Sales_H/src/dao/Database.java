package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	public Connection Get_Connection() throws Exception {
		
		String message = null;
		try {
			String connectionURL = "jdbc:mysql://localhost:3307/naturaotra";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "hurter",
					"hurtert");
			return connection;
			
		} catch (SQLException e) {

			System.err.println("Error Code: " +
                    ((SQLException)e).getErrorCode());
			message = ((SQLException)e).getMessage();
			
			throw e;
		} catch (Exception e) {
			
  	System.out.println("Error Code: " + e);

			throw e;

			
		}
	}
}