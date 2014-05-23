package cabbage.view.test;

// TEST HARNESS 2v1 - DO NOT CHANGE ANYTHING

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import cabage.model.entities.CollectibleMasterBean;
import cabbage.controller.MuffinTop;

public class TestHarness2v1 {

	private final String VERS = "2v1";

	private final int NUM_OF_PLAYERS = 10;
	private final int NUM_OF_LIVECOLLECTIBLE_IDS = 10;
	private int playerIDs[] = new int[NUM_OF_PLAYERS];
	private int playerPoints[] = new int[NUM_OF_PLAYERS];
	private int liveCollectibleIDs[];
	private int liveCollectiblePointList[];
	private MuffinTop mt;

	private final int NUM_OF_COLL_MASTERS = 127;
	private final int NUM_OF_COLL_MASTERS_FACULTY = 10;
	private final int NUM_OF_COLL_MASTERS_SKILL = 38;
	private final int NUM_OF_COLL_MASTERS_ITEM = 79;

	private final int MAX_COLL_CAT_IDs_TO_CHECK = 4;
	private final String CAT_NAMES[] = {"item","skill","faculty","NotExist"};
	private final int CAT_IDs[] = {1,2,3,4};
	private final int NUM_OF_COLL_MASTERS_BY_CAT_IDs[] = {NUM_OF_COLL_MASTERS_ITEM,NUM_OF_COLL_MASTERS_SKILL,NUM_OF_COLL_MASTERS_FACULTY,0};

	private final int VALUE_OF_ALL_LC = 57;
	private final int VALUE_LC_0 = 53;
	private final int VALUE_LC_1 = 2;
	private final int VALUE_LC_2 = 2;
	private final int LEVEL_NUMS[] = {0,1,2,3};
	private final int LC_LEVEL_VALUE[] = {VALUE_LC_0,VALUE_LC_1,VALUE_LC_2,0};

	public TestHarness2v1() throws ClassNotFoundException, SQLException {
		System.out.println("********** START " + VERS + " TEST **********");
		mt = new MuffinTop();
		buildLiveCollectibleIDArray();
		startTests();
		System.out.println("\n********** END TEST **********");
	}

	private boolean startTests() throws SQLException {

		if (!createNewAccounts()) {
			System.out.println("\nFailed to create new accounts");
			return false;
		}

		if(!insertIntoCollected()) {
			System.out.println("\nFailed to create collected records for players!");
			return false;
		}

		if(!getAllCollectibleMasters()) {
			System.out.println("\nFailed to get all collectiblemasters!");
			return false;
		}

		if(!getAllCollectibleMastersByCatID()) {
			System.out.println("\nFailed to get all collectiblemasters by collectiblecategoryid!");
			return false;
		}

		if(!getValueOfAllLiveCollectibles()) {
			System.out.println("\nFailed to correctly calculate the value of all livecollectibles!");
			return false;
		}

		if(!getValueOfAllLiveCollectiblesByLevel()) {
			System.out.println("\nFailed to correctly calculate the value of all livecollectibles by level!");
			return false;
		}

		System.out.println("\nTest success!");
		System.out.println("\nOUTSTANDING!");

		return true;
	}

	private void buildLiveCollectibleIDArray() {
		liveCollectiblePointList = new int[NUM_OF_LIVECOLLECTIBLE_IDS];
		liveCollectiblePointList[0] = 1;
		liveCollectiblePointList[1] = 5;
		liveCollectiblePointList[2] = 10;
		liveCollectiblePointList[3] = 1;
		liveCollectiblePointList[4] = 1;
		liveCollectiblePointList[5] = 10;
		liveCollectiblePointList[6] = 1;
		liveCollectiblePointList[7] = 1;
		liveCollectiblePointList[8] = 1;
		liveCollectiblePointList[9] = 1;

		liveCollectibleIDs = new int [NUM_OF_LIVECOLLECTIBLE_IDS];
		liveCollectibleIDs[0] = 1;
		liveCollectibleIDs[1] = 13;
		liveCollectibleIDs[2] = 15;
		liveCollectibleIDs[3] = 25;
		liveCollectibleIDs[4] = 9; 
		liveCollectibleIDs[5] = 28;
		liveCollectibleIDs[6] = 2;
		liveCollectibleIDs[7] = 14;
		liveCollectibleIDs[8] = 26;
		liveCollectibleIDs[9] = 3;
	}

	private int[] createArrayForPlayer(int index) {
		Random r = new Random();
		int randNum = r.nextInt(NUM_OF_LIVECOLLECTIBLE_IDS)+1;		
		int randLiveCollectibleArray[] = new int[randNum];
		int currentPoints = 0;
		for (int x = 0; x < randLiveCollectibleArray.length; x++){
			randLiveCollectibleArray[x] = liveCollectibleIDs[x];
			currentPoints += liveCollectiblePointList[x];
		}
		playerPoints[index] = currentPoints;
		return randLiveCollectibleArray;
	}

	private boolean createNewAccounts() throws SQLException {
		System.out.println("\nCreating new accounts...");

		for (int x = 0; x < playerIDs.length; x ++) {
			int id = mt.createAccount("username " + (x+1), "first name " + (x+1), "last name " + (x+1), "email" + (x+1) + "@email.com", "Password" + (x+1));
			if (id == 0) return false;
			System.out.println("new player_id: " + id + " - username " + (x+1) + " first name " + (x+1) + " last name " + (x+1) + " email" + (x+1) + "@email.com" + " Password" + (x+1));
			playerIDs[x] = id;
		}

		return true;
	}

	private boolean insertIntoCollected() throws SQLException {
		System.out.println("\nInserting into collected...");

		for (int i = 0; i < playerIDs.length; i ++) {

			System.out.println("\n - player_id " + playerIDs[i] + "...");

			int pa[] = createArrayForPlayer(i);

			for (int j = 0; j < pa.length; j ++) {
				int id = mt.insertCollected(playerIDs[i], pa[j]);
				if (id == 0) return false;
				System.out.println(" -- livecollectible_id: " + pa[j]);
				System.out.println(" ----- New collected_id: " + id);
			}

			System.out.println(playerPoints[i] + " points have been added to this player's id in the collected table");
			System.out.println("Let's hope you can find them again :o");

		}

		return true;
	}

	private boolean getAllCollectibleMasters() throws SQLException {
		System.out.println("\n##########Getting all collectible masters...");

		List<CollectibleMasterBean> returnList = mt.getAllCollectibleMasters();

		if (returnList == null) return false;
		if (returnList.size() != this.NUM_OF_COLL_MASTERS) return false;

		return true;
	}

	private boolean getAllCollectibleMastersByCatID() throws SQLException {
		System.out.println("\n##########Getting all collectible masters by categoryid...");

		for (int x = 0; x < (this.MAX_COLL_CAT_IDs_TO_CHECK) ; x ++) {
			System.out.println(" - Getting collectiblemaster list for " + this.CAT_NAMES[x]);
			List<CollectibleMasterBean> returnList = mt.getAllCollectibleMastersByCategory(this.CAT_IDs[x]);

			if ((x+1) == MAX_COLL_CAT_IDs_TO_CHECK) {
				if (returnList == null) {
					return true;
				} else {
					return false;
				}
			} else {
				if (returnList == null) return false;
				if (returnList.size() != this.NUM_OF_COLL_MASTERS_BY_CAT_IDs[x]) return false;
			}

		}

		return true;
	}

	private boolean getValueOfAllLiveCollectibles() throws SQLException {
		System.out.println("\n##########Getting value of all livecollectibles...");

		int returnValue = mt.getValueOfAllLiveCollectibles();
		if (returnValue != this.VALUE_OF_ALL_LC)  return false;

		return true;
	}

	private boolean getValueOfAllLiveCollectiblesByLevel() throws SQLException {
		System.out.println("\n##########Getting value of all livecollectibles by level...");

		for (int x = 0; x < (this.MAX_COLL_CAT_IDs_TO_CHECK) ; x ++) {
			System.out.println(" - Getting livecollectible value for level" + this.LEVEL_NUMS[x]);
			int returnNum = mt.getValueOfAllLiveCollectibles(this.LEVEL_NUMS[x]);

			if ((x+1) == this.MAX_COLL_CAT_IDs_TO_CHECK) {
				if (returnNum == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (returnNum != this.LC_LEVEL_VALUE[x]) return false;
			}

		}

		return true;
	}

}
