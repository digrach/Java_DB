package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cabbage.database.utilities.DataBaseConnection;

public class AccountCreationOps {
	
	private DataBaseConnection db;
	
	public int insertNewAccount(String username, String firstname, String lastname, String emailaddress, String password) throws SQLException {
		
		db = new DataBaseConnection();
		
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into account (username,firstname,lastname,emailaddress,password) values (?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, firstname);
		preparedStatement.setString(3, lastname);
		preparedStatement.setString(4, emailaddress);
		preparedStatement.setString(5, password);
		preparedStatement.execute();

		ResultSet rskey = preparedStatement.getGeneratedKeys();

		// Make sure the resultset is not null
		// and contains something.
		if (rskey.next()) {
			int newAccountID = rskey.getInt(1);
			con.close();
			return newAccountID;
		}
		con.close();
		return 0;
	}
	
  public int insertNewPlayer(int accountid) throws SQLException {
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into player (account_id) values (?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, accountid);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		// Make sure the resultset contains something.
		if (rskey.next()) {
			int newPlayerID = rskey.getInt(1);
			con.close();
			return newPlayerID;
		}
		con.close();
		return 0;
	}

}
