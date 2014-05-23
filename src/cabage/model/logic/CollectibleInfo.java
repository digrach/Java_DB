package cabage.model.logic;

import java.sql.SQLException;
import java.util.List;

import cabage.model.entities.CollectibleMasterBean;
import cabbage.controller.Cupcake;

public class CollectibleInfo {
	
	private Cupcake cupcake;
	
	public CollectibleInfo() {
		
	}
	
	public List<CollectibleMasterBean> getAllCollectibleMasters() throws SQLException {
		cupcake = new Cupcake();
		return cupcake.getAllCollectibleMasters();
	}
	
	public List<CollectibleMasterBean> getAllCollectibleMastersByCategory(int categoryid) throws SQLException {
		cupcake = new Cupcake();
		if (!cupcake.checkCollectibleCategoryExists(categoryid)) return null;
		return cupcake.getCollectiblesByCategory(categoryid);
	}

}
