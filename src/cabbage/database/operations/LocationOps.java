package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cabbage.database.utilities.DataBaseConnection;

public class LocationOps {
	
	private DataBaseConnection db;
	
	public LocationOps() {
		
	}
	
	/**
	 * Returns true if a given location_id exists in the location table.
	 * @param location_id - the int id to search for.
	 * @return true or false - boolean.
	 * @throws SQLException
	 */
	public boolean checkLocationExists(int location_id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM location WHERE location_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, location_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}
	
	//
	
	public boolean checkLevelExists(int levelNumber) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT z FROM location WHERE z=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, levelNumber);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

}
