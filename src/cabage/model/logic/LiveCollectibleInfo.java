package cabage.model.logic;

import java.sql.SQLException;

import cabbage.controller.Cupcake;

public class LiveCollectibleInfo {
	
	private Cupcake cupcake;
	
	public LiveCollectibleInfo() {
		
	}
	
	public int getValueOfAllLiveCollectibles() throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getValueOfAllLiveCollectibles();
	}
	
	public int getValueOfAllLiveCollectibles(int levelNumber) throws SQLException {
		cupcake = new Cupcake();
		if (!cupcake.checkLevelExists(levelNumber)) return 0;
		return cupcake.getValueOfAllLiveCollectibles(levelNumber);
	}

}
