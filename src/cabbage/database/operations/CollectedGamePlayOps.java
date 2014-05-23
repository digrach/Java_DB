package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cabbage.database.utilities.DataBaseConnection;

public class CollectedGamePlayOps {
	
	private DataBaseConnection db;
	
	public CollectedGamePlayOps() {
		
	}
	
	/**
	 * Inserts a new collected record for a player and returns the id of the new collected record.
	 * @param livecollectible_id - the int id of the livecollectible.
	 * @param player_id - the int id of the player.
	 * @return collected_id - int.
	 * @throws SQLException
	 */
	public int InsertCollected(int livecollectible_id,int player_id) throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into collected (livecollectible_id,player_id) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, livecollectible_id);
		preparedStatement.setInt(2, player_id);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}

}

/**
colleyr
*/