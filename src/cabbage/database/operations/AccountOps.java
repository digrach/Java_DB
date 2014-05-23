package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cabage.model.entities.Account;
import cabage.model.entities.Player;
import cabbage.database.utilities.DataBaseConnection;

public class AccountOps {

	private DataBaseConnection db;

	public boolean checkEmailAddressExists(String emailaddress) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT emailaddress from account where emailaddress=?");
		ps.setString(1, emailaddress);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}
	
	public boolean login(String username,String password) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT username,password from account where username=? and password=?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}


}
