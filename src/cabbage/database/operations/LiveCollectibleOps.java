package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cabbage.database.utilities.DataBaseConnection;

public class LiveCollectibleOps {

	private DataBaseConnection db;

	public LiveCollectibleOps() {

	}

	/**
	 * Returns true if a given collectiblemaster_id and location_id already exists in
	 * a record of the the livecollectible table.
	 * @param livecollectible_id - the int id to search for.
	 * @param location_id - the int id to search for.
	 * @return true or false - boolean.
	 * @throws SQLException
	 */
	public boolean checkLiveCollectibleExistsAtLocation(int collectiblemaster_id, int location_id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM livecollectible WHERE collectiblemaster_id=? and location_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, collectiblemaster_id);
		ps.setInt(2, location_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

	//

	/**
	 * Checks that a given livecollectible_id exists in the livecollectible table and returns true or false.
	 * @param livecollectible_id - the int id of the livecollectible.
	 * @return true/false - boolean.
	 * @throws SQLException
	 */
	public boolean checkLiveCollectibleExists(int liveCollectible_id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT liveCollectible_id FROM livecollectible WHERE liveCollectible_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, liveCollectible_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

	public int getValueOfAllLiveCollectibles() throws SQLException {
		return getValOfAllLiveCollectibles(-1);
	}

	public int getValueOfAllLiveCollectibles(int levelNumber) throws SQLException {
		return getValOfAllLiveCollectibles(levelNumber);
	}

	private int getValOfAllLiveCollectibles(int levelNumber) throws SQLException {
		int num = levelNumber;
		int returnValue = 0;
		String sqlString  = "select cc.value from collectiblecategory cc inner join collectiblemaster cm on cc.collectiblecategory_id = cm.collectiblecategory_id inner join livecollectible lc on cm.collectiblemaster_id = lc.collectiblemaster_id inner join location l on lc.location_id = l.location_id where lc.available = 1";
		if (num >= 0) {
			sqlString += " and l.z = " + num;
		}
		//System.out.println(sqlString);
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			returnValue += rs.getInt("cc.value");
		}
		conn.close();
		return returnValue;
	}

}
