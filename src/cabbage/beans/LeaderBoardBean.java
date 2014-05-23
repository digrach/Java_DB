package cabbage.beans;

public class LeaderBoardBean {
	
	private int player_id;
	private String username;
	private int points;
	
	public LeaderBoardBean(int player_id, String username, int points) {
		this.player_id = player_id;
		this.username = username;
		this.points = points;
	}
	
	public void updatePoints(int value) {
		points += value;
	}
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	

}
