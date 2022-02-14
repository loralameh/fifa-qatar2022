package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {
	
	private Connection connection;
	private Statement statement;
	
	public ConnectionClass() {
		String dbName = "world_cup", username = "root", password = "";
		
		//Lora Uncomment this
		String server = "127.0.0.1:3335";
		
//		String server = "localhost";
		
		try {		
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + server + "/" + dbName, username, password);
			statement = connection.createStatement();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ResultSet selectQuery(String query) {
		ResultSet result = null;
		
		if(connection != null) {			
			try {				
				result = statement.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	public ResultSet selectQuery2(String query) {
		ResultSet result = null;
		
		if(connection != null) {			
			try {				
				result = statement.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	public int nonQuery(String nonQuery) {
		int result = 0;
		if(connection != null) {		
			try {
				result = statement.executeUpdate(nonQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return result;
	}

}
