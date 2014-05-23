package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cabage.model.entities.Account;
import cabbage.database.utilities.DataBaseConnection;

public class CheckpointOps {
	
	private DataBaseConnection db;
	private final String CHECKIN_PREFIX = "superid=";
	
//// to do
	public void getCheckpointsByLevel(int levelZ) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("select c.checkpoint_id, c.checkpointtype_id, c.location_id, l.z from checkpoint c inner join location l on c.location_id=l.location_id where l.z=?");
		ps.setInt(1, levelZ);
		ResultSet rs = ps.executeQuery();
		 while(rs.next()) {
			 System.out.println(rs.getInt("c.checkpoint_id"));
			 System.out.println(rs.getInt("c.checkpointtype_id"));
			 System.out.println(rs.getInt("c.location_id"));
			 System.out.println(rs.getInt("l.z"));
		 }
		 conn.close();
	}
	
	/**
	 * A valid checkpoint has a urlstring in the checkpoint table, 
	 * which is checked against the urlstring in the location table.
	 * @param urlString - the String urlstring
	 * @return true/false - of whether the checkpoint exists.
	 * @throws SQLException
	 */
	public boolean checkCheckpointExists(String urlString) throws SQLException { // new
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement preparedStatement = 
				conn.prepareStatement("SELECT * from location l INNER JOIN checkpoint c ON l.location_id=c.location_id WHERE l.urlstring=?");
		preparedStatement.setString(1, CHECKIN_PREFIX + urlString);
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getResultSet();
		if(rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}


}
