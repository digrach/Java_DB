package cabbage.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import cabage.model.entities.CheckinBean;
import cabage.model.entities.CollectibleMasterBean;
import cabage.model.logic.AccountCreation;
import cabage.model.logic.AccountLogin;
import cabage.model.logic.CheckinInfo;
import cabage.model.logic.CheckinUser;
import cabage.model.logic.CollectedGamePlay;
import cabage.model.logic.CollectibleCreation;
import cabage.model.logic.CollectibleInfo;
import cabage.model.logic.LeaderBoardGamePLay;
import cabage.model.logic.LiveCollectibleCreation;
import cabage.model.logic.LiveCollectibleInfo;
import cabage.model.logic.PlayerInfo;
import cabbage.beans.CollectedBean;
import cabbage.beans.LeaderBoardBean;
import cabbage.database.operations.CheckinOps;
import cabbage.database.operations.PlayerOps;

public class MuffinTop {

	private AccountCreation accountCreation;
	private AccountLogin accountLogin;
	private CollectibleCreation collectibleCreation;
	private Cupcake cupcake;
	private LiveCollectibleCreation liveCollectibleCreation;
	private CheckinUser checkinUser;
	private CollectedGamePlay collectedGamePlay;
	private LeaderBoardGamePLay leaderBoardGamePLay;
	private CollectibleInfo collectibleInfo;
	private LiveCollectibleInfo liveCollectibleInfo;
	private PlayerInfo playerInfo;
	private CheckinInfo checkinInfo;

	public boolean login(String username, String password) throws SQLException {
		accountLogin = new AccountLogin();
		return accountLogin.loginUser(username, password);
	}

	public int createAccount(String username, String firstname, String lastname, 
			String emailaddress, String password) throws SQLException {

		accountCreation = new AccountCreation();
		accountCreation.createAccount(username, firstname, lastname, emailaddress, password);
		return accountCreation.getNewPlayerID();
	}

	public int createFaculty() throws SQLException {
		collectibleCreation = new CollectibleCreation();
		return collectibleCreation.createFaculty();
	}

	public int createSkill(int facultyid) throws SQLException {
		collectibleCreation = new CollectibleCreation();
		return collectibleCreation.createSkill(facultyid);
	}

	public int createItem(int facultyid,int skillid) throws SQLException {
		collectibleCreation = new CollectibleCreation();
		return collectibleCreation.createItem(facultyid, skillid);
	}

	public int createCollectible(int itemid, int skillid, int facultyid,
			String name, int collectiblecategoryid, String description, String imagefilename) throws SQLException {
		collectibleCreation = new CollectibleCreation();
		return collectibleCreation.createCollectible(itemid, skillid, facultyid, name, collectiblecategoryid, description, imagefilename);
	}

	public List<CollectibleMasterBean> getCollectiblesByCategory(int categoryid) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getCollectiblesByCategory(categoryid);
	}

	public int createLiveCollectible(int collectiblemaster_id, int location_id) throws SQLException {
		liveCollectibleCreation = new LiveCollectibleCreation();
		return liveCollectibleCreation.createLiveCollectible(collectiblemaster_id, location_id);
	}

	// new

	public CheckinBean doCheckin(String urlString) throws SQLException {
		checkinUser = new CheckinUser();
		return checkinUser.doCheckin(urlString);
	}

	public CheckinBean getCheckinByID(int checkin_id) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getCheckinByID(checkin_id);
	}

	public List<CheckinBean> getAllCheckins() throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getAllCheckins();
	}

	public int insertCollected(int player_id, int livecollectible_id) throws SQLException {
		collectedGamePlay = new CollectedGamePlay();
		return collectedGamePlay.insertCollected(player_id, livecollectible_id);
	}

	public int getPlayerPoints(int player_id) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getPlayerPoints(player_id);
	}

	public List<Integer> getAllPlayerPoints() throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getAllPlayerPoints();
	}

	public Map<Integer,Integer> getAllPlayerPointsMap() throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getAllPlayerPointsMap();
	}

	public List<LeaderBoardBean> getLeaderBoardBeans() throws SQLException {
		leaderBoardGamePLay = new LeaderBoardGamePLay();
		return leaderBoardGamePLay.getLeaderBoardBeans();
	}

	public List<CollectibleMasterBean> getAllCollectibleMasters() throws SQLException {
		collectibleInfo = new CollectibleInfo();
		return collectibleInfo.getAllCollectibleMasters();
	}

	public List<CollectibleMasterBean> getAllCollectibleMastersByCategory(int categoryid) throws SQLException {
		collectibleInfo = new CollectibleInfo();
		return collectibleInfo.getAllCollectibleMastersByCategory(categoryid);
	}

	public int getValueOfAllLiveCollectibles() throws SQLException {
		liveCollectibleInfo = new LiveCollectibleInfo();
		return liveCollectibleInfo.getValueOfAllLiveCollectibles();
	}
	
	public int getValueOfAllLiveCollectibles(int levelNumber) throws SQLException {
		liveCollectibleInfo = new LiveCollectibleInfo();
		return liveCollectibleInfo.getValueOfAllLiveCollectibles(levelNumber);
	}
	
	public List<CollectedBean> getAllPlayerCollectibles(int playerid) throws SQLException {
		playerInfo = new PlayerInfo();
		return playerInfo.getAllPlayerCollectibles(playerid);
	}
	
	public JSONArray getCheckinBeanJSONArray2() throws SQLException, JSONException {
		checkinInfo = new CheckinInfo();
		return checkinInfo.getCheckinBeanJSONArray2();
	}

}
