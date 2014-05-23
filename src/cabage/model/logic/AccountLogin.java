package cabage.model.logic;

import java.sql.SQLException;

import cabbage.controller.Cupcake;

public class AccountLogin {
	
	private Cupcake dc; 
	
	public AccountLogin() {
		
	}
	
	public boolean loginUser(String username, String password) throws SQLException {
		// value class????
		dc = new Cupcake();
		return dc.login(username, password);
	}

}
