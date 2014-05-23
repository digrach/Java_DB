package cabage.model.logic;

import java.sql.SQLException;

import cabbage.controller.Cupcake;
import cabbage.utilities.CabbageUtility;

public class AccountCreation {

	private final int MIN_USERNAME = 6;
	private final int MAX_USERNAME = 20;
	private final int MIN_FIRSTNAME = 3;
	private final int MAX_FIRSTNAME = 50;
	private final int MIN_LASTNAME = 3;
	private final int MAX_LASTNAME = 50;
	private final int MIN_EMAILADDRESS = 1;
	private final int MAX_EMAILADDRESS = 150;
	private final int MIN_PASSWORD = 6;
	private final int MAX_PASSWORD = 24;

	private Cupcake dbc; // = new DatabaseCupCake();
	
	private int newPlayerID;

	public AccountCreation() {

	}

	public boolean createAccount(String username, String firstname, String lastname, 
			String emailaddress, String password) throws SQLException {
		
		newPlayerID = 0;

		if (validateUsername(username) 
				&& validateFirstName(firstname) 
				&& validateLastname(lastname)
				&& validateEmailAddress(emailaddress)
				&& validatePassword(password,username)) {

			// True - insert new account here
			dbc = new Cupcake();

			int newAccountID = dbc.insertNewAccount(username, firstname, lastname, emailaddress, password);

			// if success
			if(newAccountID > 0) {
				// insert new player
				newPlayerID = dbc.insertNewPlayer(newAccountID);
			} else {
				return false;
			}

			return true;
		}

		return false;
	}

	public boolean validateUsername(String username){
		if(CabbageUtility.checkLengthOfString(username, MIN_USERNAME, MAX_USERNAME)) return true;
		return false;
	}

	public boolean validateFirstName(String firstname){
		if(CabbageUtility.checkLengthOfString(firstname, MIN_FIRSTNAME, MAX_FIRSTNAME)) return true;
		return false;
	}

	public boolean validateLastname(String lastname){
		if(CabbageUtility.checkLengthOfString(lastname, MIN_LASTNAME, MAX_LASTNAME)) return true;
		return false;
	}

	public boolean validateEmailAddress(String emailaddress) throws SQLException{
		if(! CabbageUtility.checkLengthOfString(emailaddress, MIN_EMAILADDRESS, MAX_EMAILADDRESS)) return false;
		if(! CabbageUtility.isEmailFormat(emailaddress)) return false;
		dbc = new Cupcake(); // check this last.
		if(dbc.checkEmailAddressExists(emailaddress)) return false;

		return true;
	}

	public boolean validatePassword(String password, String username){
		if(! CabbageUtility.checkLengthOfString(password, MIN_PASSWORD, MAX_PASSWORD)) return false;
		if(! CabbageUtility.containsLower(password)) return false;
		if(! CabbageUtility.containsUpper(password)) return false;
		if(! CabbageUtility.containsNumber(password)) return false;
		if(CabbageUtility.matchTwoStrings(password, username)) return false;
		return true;
	}
	
	public int getNewPlayerID(){
		return newPlayerID;
	}





}
