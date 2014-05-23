package cabage.model.logic;

import java.sql.SQLException;
import java.util.List;

import cabbage.beans.CollectedBean;
import cabbage.controller.Cupcake;

public class PlayerInfo {
	
	private Cupcake cupcake;
	
	public PlayerInfo() {
		
	}
	
	public List<CollectedBean> getAllPlayerCollectibles(int playerid) throws SQLException {
		cupcake = new Cupcake();
		if (!cupcake.checkPlayerExists(playerid)) return null;
		return cupcake.getAllPlayerCollectibles(playerid);
	}

}
