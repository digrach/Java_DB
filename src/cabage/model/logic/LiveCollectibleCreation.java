package cabage.model.logic;

import java.sql.SQLException;

import cabbage.controller.Cupcake;

public class LiveCollectibleCreation {
	
	private Cupcake cc;
	
	public LiveCollectibleCreation() {
		
	}
	
	public int createLiveCollectible(int collectiblemaster_id, int location_id) throws SQLException {
		if (collectiblemaster_id == 0) return 0;
		if (location_id == 0) return 0;
		if (!validateLocationID(location_id)) return 0;
		if (!validateCollectibleMasterID(collectiblemaster_id)) return 0;
		if (checkForDuplicate(collectiblemaster_id,location_id)) return 0;
		return cc.InsertNewLiveCollectible(collectiblemaster_id, location_id);
	}
	
	private boolean validateCollectibleMasterID(int collectiblemaster_id) throws SQLException {
		cc = new Cupcake();
		return cc.checkCollectibleMasterExists(collectiblemaster_id);
	}
	
	private boolean validateLocationID(int location_id) throws SQLException {
		cc = new Cupcake();
		return cc.checkLocationExists(location_id);
	}
	
	private boolean checkForDuplicate(int collectiblemaster_id, int location_id) throws SQLException {
		cc = new Cupcake();
		return cc.checkLiveCollectibleExistsAtLocation(collectiblemaster_id, location_id);
	}
	
	

}
