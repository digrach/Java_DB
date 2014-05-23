package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cabage.model.entities.Account;
import cabage.model.entities.Player;
import cabbage.beans.CollectedBean;
import cabbage.beans.LeaderBoardBean;
import cabbage.database.utilities.DataBaseConnection;

public class PlayerOps {

	private DataBaseConnection db;

	public List<Account> getPlayerAccounts() throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT a.account_id,a.username,a.firstname,a.lastname,a.emailaddress,a.password,p.player_id FROM account a INNER JOIN player p ON a.account_id=p.account_id;");
		ResultSet rs = ps.executeQuery();
		List<Account> accountList = new ArrayList<Account>();
		while (rs.next()) {
			Player p = new Player(rs.getInt("a.account_id"),rs.getInt("p.player_id"));
			Account a = new Account(rs.getInt("a.account_id"),rs.getString("a.username"),rs.getString("a.password"),rs.getString("a.firstname"),rs.getString("a.lastname"),rs.getString("a.emailaddress"),p);
			accountList.add(a);
		}

		conn.close();

		return accountList;
	}

	//

	/**
	 * Returns true if a given player_id exists in the player table.
	 * @param player_id - the int id of the player to search for.
	 * @return true or false - boolean.
	 * @throws SQLException
	 */
	public boolean checkPlayerExists(int player_id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT player_id FROM player WHERE player_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, player_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

	/**
	 * Returns true if a given player_id has a given livecollectible_id in the collected table.
	 * @param livecollectible_id - the int id of the livecollectible.
	 * @param player_id - the int id of the player.
	 * @return true or false - boolean.
	 * @throws SQLException
	 */
	public boolean checkPlayerHasCollected(int liveCollectible_id, int player_id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT player_id FROM collected WHERE player_id=? and livecollectible_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, player_id);
		ps.setInt(2, liveCollectible_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

	public int getPlayerPoints(int player_id) throws SQLException {
		int points = 0;		

		DataBaseConnection db;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "select SUM(cc.value) from collectiblecategory cc inner join collectiblemaster cm on cm.collectiblecategory_id = cc.collectiblecategory_id inner join livecollectible lc  on lc.collectiblemaster_id = cm.collectiblemaster_id  inner join collected c on c.livecollectible_id = lc.livecollectible_id  where c.player_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, player_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			points = rs.getInt(1);
		}
		conn.close();
		return points;

	}

	public List<Integer> getAllPlayerPoints() throws SQLException {
		List<Integer> pointsList = new ArrayList<Integer>();

		DataBaseConnection db;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "select SUM(cc.value) from collectiblecategory cc inner join collectiblemaster cm on cm.collectiblecategory_id = cc.collectiblecategory_id inner join livecollectible lc  on lc.collectiblemaster_id = cm.collectiblemaster_id  inner join collected c on c.livecollectible_id = lc.livecollectible_id inner join player p on c.player_id = p.player_id group by c.player_id";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			pointsList.add(rs.getInt(1));
		}

		conn.close();

		return pointsList;
	}

	public Map<Integer,Integer> getAllPlayerPointsMap() throws SQLException {
		Map<Integer,Integer> pointsList = new HashMap<Integer,Integer>();

		DataBaseConnection db;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "select SUM(cc.value), c.player_id from collectiblecategory cc inner join collectiblemaster cm on cm.collectiblecategory_id = cc.collectiblecategory_id inner join livecollectible lc  on lc.collectiblemaster_id = cm.collectiblemaster_id  inner join collected c on c.livecollectible_id = lc.livecollectible_id inner join player p on c.player_id = p.player_id group by c.player_id";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			pointsList.put(rs.getInt(2),rs.getInt(1));
		}

		conn.close();

		return pointsList;
	}

	public List<LeaderBoardBean> getAllPlayerCollectibles() throws SQLException {
		List<LeaderBoardBean> list = null;
		Map<Integer,LeaderBoardBean> m = new HashMap<Integer,LeaderBoardBean>();

		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "select cm.name, cc.value, c.player_id, a.username from collectiblemaster cm inner join collectiblecategory cc on cm.collectiblecategory_id = cc.collectiblecategory_id inner join livecollectible lc on lc.collectiblemaster_id = cm.collectiblemaster_id inner join collected c on c.livecollectible_id = lc.livecollectible_id inner join player p on p.player_id = c.player_id inner join account a on a.account_id = p.account_id order by a.username";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			String name = rs.getString("cm.name");
			int value = rs.getInt("cc.value");
			int playerid = rs.getInt("player_id");
			String username = rs.getString("a.username");

			if (! m.containsKey(playerid)) {
				m.put(playerid, new LeaderBoardBean(playerid,username,value));
			} else {
				m.get(playerid).updatePoints(value);
			}

		}

		conn.close();

		if (! m.isEmpty()) {
			list = new ArrayList<LeaderBoardBean>(m.values());
		}

		return list;
	}

	public List<CollectedBean> getAllPlayerCollectibles(int playerid) throws SQLException {
		List<CollectedBean> l = new ArrayList<CollectedBean>();

		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT c.player_id, c.livecollectible_id, lc.location_id, cm.name, cm.description, cm.imagefilename, cm.collectiblecategory_id, cc.name, cc.value from collected c inner join livecollectible lc on c.livecollectible_id = lc.livecollectible_id inner join collectiblemaster cm on lc.collectiblemaster_id = cm.collectiblemaster_id inner join collectiblecategory cc on cm.collectiblecategory_id = cc.collectiblecategory_id where c.player_id =?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, playerid);
		ResultSet rs = ps.executeQuery();

		l = makeCollectedBeanList(rs);
		conn.close();

		return l;

	}

	private List<CollectedBean> makeCollectedBeanList(ResultSet rs) throws SQLException {
		List<CollectedBean> l = new ArrayList<CollectedBean>();

		while (rs.next()) {
			int playerid = rs.getInt("c.player_id");
			int livecollectibleid = rs.getInt("c.livecollectible_id");
			int locationid = rs.getInt("lc.location_id");
			int value = rs.getInt("cc.value");
			String name = rs.getString("cm.name");
			String description = rs.getString("cm.description");
			String imagefilename = rs.getString("cm.imagefilename");
			int collectiblecategoryid = rs.getInt("cm.collectiblecategory_id");
			String collectiblecategoryname = rs.getString("cc.name");
			
			CollectedBean b = new CollectedBean(playerid, livecollectibleid, locationid, value, name, 
					description, imagefilename, collectiblecategoryid, collectiblecategoryname);
			
			l.add(b);
		}

		return l;

	}


}
