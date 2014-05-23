package cabbage.view.test;

import java.sql.Connection;
import java.sql.SQLException;

import cabbage.database.utilities.DataBaseConnection;


public class Start {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		DataBaseConnection dbConn = new DataBaseConnection();
		Connection con = dbConn.getConnection();

	}
}




