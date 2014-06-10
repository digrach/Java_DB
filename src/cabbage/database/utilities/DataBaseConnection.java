package cabbage.database.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	public DataBaseConnection() {

	}

	public Connection getConnection() {
		//System.out.println("-------- MySQL JDBC Connection Testing ------------");

//		Connection connection = null;
//		String address = "jdbc:mysql://localhost:";
//		String portNumber = "3306";
//		String databaseName = "/cabbagedb";
//		String username = "root";
//		String password = "";
//		String connectionString = address + portNumber + databaseName;
//		
//		Connection connection = null;
//		String address = "jdbc:mysql://localhost:";
//		String portNumber = "8889";
//		String databaseName = "/cabbagedb";
//		String username = "root";
//		String password = "root";
//		String connectionString = address + portNumber + databaseName;
		
		
		Connection connection = null;
		String address = "jdbc:mysql://localhost:";
		String portNumber = "3306";
		String databaseName = "/testdatabase";
		String username = "root";
		String password = "password";
		String connectionString = address + portNumber + databaseName;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return connection;
		}

		//System.out.println("MySQL JDBC Driver Registered!");


		try {
			connection = DriverManager
					.getConnection(connectionString,username,password);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return connection;
		}

		if (connection != null) {
			//System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		System.out.println("SUCCESS");
		return connection;

	}

}
