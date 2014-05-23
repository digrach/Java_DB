package cabbage.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import cabage.model.entities.CheckinBean;
import cabage.model.entities.CollectibleMasterBean;
import cabbage.beans.CollectedBean;
import cabbage.beans.LeaderBoardBean;
import cabbage.database.operations.AccountCreationOps;
import cabbage.database.operations.AccountOps;
import cabbage.database.operations.CheckinOps;
import cabbage.database.operations.CheckinUserOps;
import cabbage.database.operations.CheckpointOps;
import cabbage.database.operations.CollectibleCreationOps;
import cabbage.database.operations.CollectedGamePlayOps;
import cabbage.database.operations.CollectibleOps;
import cabbage.database.operations.LiveCollectibleCreationOps;
import cabbage.database.operations.LiveCollectibleOps;
import cabbage.database.operations.LocationOps;
import cabbage.database.operations.PlayerOps;


public class Cupcake {

	private AccountCreationOps accountCreationOps;
	private AccountOps accountOps;
	private CollectibleCreationOps collectibleCreationOps;
	private CollectibleOps collectibleOps;
	private LocationOps locationOps;
	private LiveCollectibleOps liveCollectibleOps;
	private LiveCollectibleCreationOps liveCollectibleCreationOps;
	private CheckinOps checkinOps;
	private CheckpointOps checkpointOps;
	private CheckinUserOps checkinUserOps;
	private PlayerOps playerOps;
	private CollectedGamePlayOps collectedGamePlayOps;
	

	public int insertNewAccount(String username, String firstname, 
			String lastname, String emailaddress, String password) throws SQLException {
		accountCreationOps = new AccountCreationOps();
		return accountCreationOps.insertNewAccount(username, firstname, lastname, emailaddress, password);
	}

	public boolean checkEmailAddressExists(String emailaddress) throws SQLException {
		accountOps = new AccountOps();
		return accountOps.checkEmailAddressExists(emailaddress);
	}

	public int insertNewPlayer(int accountid) throws SQLException {
		accountCreationOps = new AccountCreationOps();
		return accountCreationOps.insertNewPlayer(accountid);
	}

	public boolean login(String username,String password) throws SQLException {
		accountOps = new AccountOps();
		return accountOps.login(username, password);
	}

	public int insertNewCollectible(int itemid, int skillid, int facultyid,
			String name, int collectiblecategoryid, String description, String imagefilename) throws SQLException {
		collectibleCreationOps = new CollectibleCreationOps();
		return collectibleCreationOps.insertNewCollectible(itemid, skillid, facultyid, name, collectiblecategoryid, description, imagefilename);
	}

	public int insertNewFaculty() throws SQLException {
		collectibleCreationOps = new CollectibleCreationOps();
		return collectibleCreationOps.insertNewFaculty();
	}

	public int insertNewSkill(int facultyid) throws SQLException {
		collectibleCreationOps = new CollectibleCreationOps();
		return collectibleCreationOps.insertNewSkill(facultyid);
	}

	public int insertNewItem(int facultyid, int skillid) throws SQLException {
		collectibleCreationOps = new CollectibleCreationOps();
		return collectibleCreationOps.insertNewItem(facultyid, skillid);
	}

	public CollectibleMasterBean getCollectibleMaster(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getCollectibleMaster(name);
	}

	public CollectibleMasterBean getCollectibleMaster(int id) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getCollectibleMaster(id);
	}

	public List<CollectibleMasterBean> searchCollectibleMaster(String searchString) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.searchCollectibleMaster(searchString);
	}

	public int getCollectibleCategoryid(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getCollectibleCategoryid(name);
	}

	public int getFacultyid(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getFacultyid(name);
	}

	public int getSkillid(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getSkillid(name);
	}

	public int getItemid(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getItemid(name);
	}

	public boolean checkFacultyidExists(int id) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkFacultyidExists(id);
	}

	public boolean checkSkillidExists(int id) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkSkillidExists(id);
	}

	public boolean checkItemidExists(int id) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkItemidExists(id);
	}

	public boolean checkNameExists(String name) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkNameExists(name);
	}

	public boolean checkCollectibleCategoryExists(int id) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkCollectibleCategoryExists(id);
	}

	public List<CollectibleMasterBean> getCollectiblesByCategory(int categoryid) throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getCollectiblesByCategory(categoryid);
	}

	public boolean checkCollectibleMasterExists(int collectiblemaster_id) throws SQLException{
		collectibleOps = new CollectibleOps();
		return collectibleOps.checkCollectibleMasterExists(collectiblemaster_id);
	}

	public boolean checkLocationExists(int location_id) throws SQLException {
		locationOps = new LocationOps();
		return locationOps.checkLocationExists(location_id);
	}

	public boolean checkLiveCollectibleExistsAtLocation(int collectiblemaster_id, int location_id) throws SQLException {
		liveCollectibleOps = new LiveCollectibleOps();
		return liveCollectibleOps.checkLiveCollectibleExistsAtLocation(collectiblemaster_id, location_id);
	}

	public int InsertNewLiveCollectible(int collectiblemaster_id,int location_id) throws SQLException {
		liveCollectibleCreationOps = new LiveCollectibleCreationOps();
		return liveCollectibleCreationOps.InsertNewLiveCollectible(collectiblemaster_id, location_id);
	}

	// new...

	public boolean checkCheckpointExists(String urlString) throws SQLException { //new
		checkpointOps = new CheckpointOps();
		return checkpointOps.checkCheckpointExists(urlString);
	}
	
	public CheckinBean checkin(String urlString) throws SQLException {
		checkinUserOps = new CheckinUserOps();
		return checkinUserOps.checkin(urlString);
	}
	
	public CheckinBean getCheckinByID(int checkin_id) throws SQLException {
		checkinOps = new CheckinOps();
		return checkinOps.getCheckinByID(checkin_id);
	}
	
	public List<CheckinBean> getAllCheckins() throws SQLException {
		checkinOps = new CheckinOps();
		return checkinOps.getAllCheckins();
	}
	
	// 
	
	public boolean checkLiveCollectibleExists(int liveCollectible_id) throws SQLException {
		liveCollectibleOps = new LiveCollectibleOps();
		return liveCollectibleOps.checkLiveCollectibleExists(liveCollectible_id);
	}
	
	public boolean checkPlayerExists(int player_id) throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.checkPlayerExists(player_id);
	}
	
	public boolean checkPlayerHasCollected(int liveCollectible_id, int player_id) throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.checkPlayerHasCollected(liveCollectible_id, player_id);
	}
	
	public int InsertCollected(int livecollectible_id,int player_id) throws SQLException {
		collectedGamePlayOps = new CollectedGamePlayOps();
		return collectedGamePlayOps.InsertCollected(livecollectible_id, player_id);
	}
	
	public int getPlayerPoints(int player_id) throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.getPlayerPoints(player_id);
	}
	
	public Map<Integer,Integer> getAllPlayerPointsMap() throws SQLException {
		playerOps = new PlayerOps();
			return playerOps.getAllPlayerPointsMap();
	}

	public List<Integer> getAllPlayerPoints() throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.getAllPlayerPoints();
	}
	
	public List<LeaderBoardBean> getAllPlayerCollectibles() throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.getAllPlayerCollectibles();
	}
	
	public List<CollectibleMasterBean> getAllCollectibleMasters() throws SQLException {
		collectibleOps = new CollectibleOps();
		return collectibleOps.getAllCollectibleMasters();
	}
	
	public int getValueOfAllLiveCollectibles() throws SQLException {
		liveCollectibleOps = new LiveCollectibleOps();
		return liveCollectibleOps.getValueOfAllLiveCollectibles();
	}
	
	public int getValueOfAllLiveCollectibles(int levelNumber) throws SQLException {
		liveCollectibleOps = new LiveCollectibleOps();
		return liveCollectibleOps.getValueOfAllLiveCollectibles(levelNumber);
	}
	
	public boolean checkLevelExists(int levelNumber) throws SQLException {
		locationOps = new LocationOps();
		return locationOps.checkLevelExists(levelNumber);
	}
	
	public List<CollectedBean> getAllPlayerCollectibles(int playerid) throws SQLException {
		playerOps = new PlayerOps();
		return playerOps.getAllPlayerCollectibles(playerid);
	}
	
	public JSONArray getCheckinBeanJSONArray2() throws SQLException, JSONException {
		checkinOps = new CheckinOps();
		return checkinOps.getCheckinBeanJSONArray2();
	}


}
