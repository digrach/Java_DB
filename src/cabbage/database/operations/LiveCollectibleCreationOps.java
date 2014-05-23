package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cabbage.database.utilities.DataBaseConnection;

public class LiveCollectibleCreationOps {
	
	private DataBaseConnection db;
	
	public LiveCollectibleCreationOps() {
		
	}
	
	/**
	 * Inserts a new livecollectible into livecollectible table. 
	 * @return id - the new id.
	 * @throws SQLException
	 */
	public int InsertNewLiveCollectible(int collectiblemaster_id,int location_id) throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into livecollectible (collectiblemaster_id,location_id,available) values (?,?,1)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, collectiblemaster_id);
		preparedStatement.setInt(2, location_id);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}

}
