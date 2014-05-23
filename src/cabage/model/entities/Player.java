package cabage.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Player {

	private int playerid;
	private int accountid;
	private Map<Integer,List<Integer>> collectibleMap;

	public Player(int playerid, int accountid) {
		this.playerid = playerid;
		this.accountid = accountid;
		collectibleMap = new TreeMap<Integer,List<Integer>>();
	}

	public void addCollectible(int key, int value) {
		if(collectibleMap.containsKey(key)) {
			collectibleMap.get(key).add(value);
		}else {
			collectibleMap.put(key, new ArrayList<Integer>());
			collectibleMap.get(key).add(value);
		}
	}

	public boolean removeCollectible(int key, int value) {
		if(collectibleMap.containsKey(key)) {
			if(collectibleMap.get(key).contains(value)) {
				collectibleMap.get(key).remove(value);
				return true;
			}
		}
		return false;
	}

	public int getPlayerid() {
		return playerid;
	}
	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public Map<Integer, List<Integer>> getCollectibleMap() {
		return collectibleMap;
	}
	public void setCollectibleMap(Map<Integer, List<Integer>> collectibleMap) {
		this.collectibleMap = collectibleMap;
	}


}
