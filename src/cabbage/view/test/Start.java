package cabbage.view.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cabage.model.entities.CollectibleMasterBean;
import cabage.model.logic.LiveCollectibleCreation;
import cabbage.controller.MuffinTop;
import cabbage.database.utilities.DataBaseConnection;


public class Start {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		
		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 1);
		Date date = cal.getTime();             
		SimpleDateFormat checkinDate = new SimpleDateFormat("EEE, MMM d, ''yy");
		String date1 = checkinDate.format(date); 
		System.out.println(date1);
		SimpleDateFormat checkinTime = new SimpleDateFormat("h:mm a");
		String date2 = checkinTime.format(date); 
		System.out.println(date2);
		//TestHarness2v1 th = new TestHarness2v1();
		

//		MuffinTop mt = new MuffinTop();
//		
//		//System.out.println(mt.getValueOfAllLiveCollectibles());
//		System.out.println(mt.getValueOfAllLiveCollectibles(0));
//		System.out.println(mt.getValueOfAllLiveCollectibles(1));
//		System.out.println(mt.getValueOfAllLiveCollectibles(2));
//		System.out.println(mt.getValueOfAllLiveCollectibles(3));

//		List<CollectibleMasterBean> cmb = mt.getAllCollectibleMasters();
//
//		System.out.println(cmb.size());
//
//		for (int x = 0; x < cmb.size(); x ++){
//			System.out.println(x);
//			System.out.println(cmb.get(x).getName());
//			System.out.println(cmb.get(x).getCollectiblemaster_id());
//			System.out.println();
//		}
//
//		List<CollectibleMasterBean> cmbc = mt.getAllCollectibleMastersByCategory(4);
//
//		System.out.println(cmbc.size());
//
//		for (int x = 0; x < cmbc.size(); x ++){
//			System.out.println(x);
//			System.out.println(cmbc.get(x).getName());
//			System.out.println(cmbc.get(x).getCollectiblemaster_id());
//			System.out.println("cc id: " + cmbc.get(x).getCollectiblecategory_id());
//			System.out.println();
//		}

		//TestHarnessPlayerPoints thpp = new TestHarnessPlayerPoints();


		//		MuffinTop mt = new MuffinTop();
		//		int numOfPlayers = 10;
		//		int lcid[] = {1,2,3};
		//		int newlcid[] = new int[4];
		//		int playerid[] = new int[numOfPlayers];
		//
		//		LiveCollectibleCreation lc = new LiveCollectibleCreation();
		//		newlcid[0] = lc.createLiveCollectible(49, 200); // catid 1
		//		newlcid[1] = lc.createLiveCollectible(11, 200); // catid 2
		//		newlcid[2] = lc.createLiveCollectible(1, 201); // catid 3
		//		newlcid[3] = lc.createLiveCollectible(10, 202); // catid 3
		//		System.out.println("Creating new live collectibles");
		//		for (int p = 0; p < newlcid.length; p ++) {
		//			System.out.println("new lcid: " + newlcid[p]);
		//		}
		//
		//		// Create accounts
		//		for (int x = 0; x < numOfPlayers; x ++) {
		//			int id = mt.createAccount("username " + x, "first name " + x, "last name " + x, "email" + x + "@email.com", "Password" + x);
		//			System.out.println("p_id: " + id);
		//			playerid[x] = id;
		//		}
		//
		//		// add to players
		//		for (int i = 0; i < playerid.length; i ++) {
		//			for (int j = 0; j < newlcid.length; j ++) {
		//				mt.insertCollected(playerid[i], newlcid[j]);
		//			}
		//		}
		//		
		//		List<Integer> pointsList = mt.getAllPlayerPoints();
		//		for (int z = 0; z < pointsList.size(); z++){
		//			System.out.println((z + 1) + " " + pointsList.get(z));
		//		}
		//		
		//		for (int y = 0; y < playerid.length; y++){
		//			System.out.println((y+1) + " " + mt.getPlayerPoints(playerid[y]));
		//		}


	}
}




