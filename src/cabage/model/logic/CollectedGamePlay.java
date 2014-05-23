package cabage.model.logic;
import java.sql.SQLException;

/**
colleyr
*/
import cabbage.controller.Cupcake;

public class CollectedGamePlay {
	
	private Cupcake cupcake;
	
	public CollectedGamePlay() {
		
	}
	
	public int insertCollected(int player_id, int livecollectible_id) throws SQLException {
		if (!checkPlayerExists(player_id)) return 0;
		if (!checkLiveCollectibleExists(livecollectible_id)) return 0;
		if ( checkForDuplicate(player_id,livecollectible_id)) return 0;
		return cupcake.InsertCollected(livecollectible_id, player_id);
	}
	
	private boolean checkPlayerExists(int player_id) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.checkPlayerExists(player_id);
	}
	
	private boolean checkLiveCollectibleExists(int livecollectible_id) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.checkLiveCollectibleExists(livecollectible_id);
	}
	
	private boolean checkForDuplicate(int player_id, int livecollectible_id) throws SQLException {
		cupcake = new Cupcake();
		return cupcake.checkPlayerHasCollected(livecollectible_id, player_id);
	}

}


