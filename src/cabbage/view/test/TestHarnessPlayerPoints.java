package cabbage.view.test;

// TEST HARNESS V1 - DO NOT CHANGE ANYTHING

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import cabbage.beans.LeaderBoardBean;
import cabbage.controller.MuffinTop;

public class TestHarnessPlayerPoints {

	private final int NUM_OF_PLAYERS = 10;
	private final int NUM_OF_LIVECOLLECTIBLE_IDS = 10;
	private int playerIDs[] = new int[NUM_OF_PLAYERS];
	private int playerPoints[] = new int[NUM_OF_PLAYERS];
	private int liveCollectibleIDs[];
	private int liveCollectiblePointList[];
	private MuffinTop mt;

	public TestHarnessPlayerPoints() throws ClassNotFoundException, SQLException {
		System.out.println("********** START TEST **********");
		mt = new MuffinTop();
		buildLiveCollectibleIDArray();
		startTests();
		System.out.println("********** END TEST **********");
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

		if(!getAllPlayerPoints()) {
			System.out.println("\nCalculations of player points failed!");
			return false;
		}
		
		if(!checkLeaderBoardSort()) {
			System.out.println("\nLeaderboard sort failed!");
			return false;
		}
		
		printLeaderBoard();
		
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
	
	private boolean getAllPlayerPoints() throws SQLException {
		System.out.println("\nGetting all player points...");

		List<LeaderBoardBean> l = mt.getLeaderBoardBeans();
		if (l.size() != playerIDs.length) return false;
		
		List<LeaderBoardBean> newList = new ArrayList<LeaderBoardBean>();
		int pos[] = new int[NUM_OF_PLAYERS];
		int pointpos[] = new int[NUM_OF_PLAYERS];
		for (int x = 0; x < playerIDs.length; x++){
			
			for (int i = 0; i < playerIDs.length; i ++){
				
				if (l.get(x).getPlayer_id() == playerIDs[i]) {
					newList.add(l.get(x));
					pos[x] = i;
					pointpos[x] = playerPoints[i];
					break;
				}
				
			}
			
		}
		
		for (int p = 0; p < newList.size();p++){
			if (!(newList.get(p).getPoints() == pointpos[p])) return false;
		}
	
		return true;
	}
	
	private boolean checkLeaderBoardSort() throws SQLException {
		System.out.println("\nChecking the leaderBoard Sorting...");

		List<LeaderBoardBean> l = mt.getLeaderBoardBeans();
		if (l.size() != playerIDs.length) return false;
		
		for (int x = 0; x< l.size() - 1; x ++) {
			if (l.get(x).getPoints() > l.get(x+1).getPoints()) return false;
			
			System.out.print(l.get(x).getPoints() + ", ");
		}
		
		System.out.print("\n");
		
		return true;
	}
	
	private void printLeaderBoard() throws SQLException {
		System.out.print("\n");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||CABBAGE LEADER BOARD|||||||||||||||||||||||||||||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

		List<LeaderBoardBean> l = mt.getLeaderBoardBeans();
		for (int x = 0; x< l.size() - 1; x ++) {
			System.out.println(l.get(x).getPoints() + " points - " + l.get(x).getUsername() + " - " + l.get(x).getPlayer_id());
		}
		
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}


}
