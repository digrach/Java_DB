package cabage.model.entities;

public class Account {
	
	private int accountid;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Player player;
	
	public Account(int accountid, String username, String password,
			String firstName, String lastName, String emailAddress,
			Player player) {
		this.accountid = accountid;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.player = player;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	
	

}
