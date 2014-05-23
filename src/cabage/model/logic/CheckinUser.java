package cabage.model.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cabage.model.entities.CheckinBean;
import cabbage.controller.Cupcake;
import cabbage.utilities.CabbageUtility;


public class CheckinUser {

	private final int URLSTRING_MIN = 7;
	private final int URLSTRING_MAX = 7;
	private final int X_STARTPOS=0;
	private final int X_ENDPOS=2;
	private final int Y_STARTPOS=2;
	private final int Y_ENDPOS=4;
	private final int Z_STARTPOS=4;
	private final int Z_ENDPOS=6;
	private final int X_MIN=1;
	private final int X_MAX=153;
	private final int Y_MIN=1;
	private final int Y_MAX=88;
	private final int Z_MIN=00;
	private final int Z_MAX=40;
	private final int Z_MINCHARS=2;
	private final int Z_MAXCHARS=2;

	private Cupcake cupcake;

	public CheckinUser() {

	}
	
	public CheckinBean doCheckin(String urlString) throws SQLException {
		if (!validateCheckin(urlString)) return null;
		return cupcake.checkin(urlString);
	}

	private boolean validateCheckin(String urlString) throws SQLException {
		if (urlString == null) return false;
		if (! (CabbageUtility.checkLengthOfString(urlString,URLSTRING_MIN,URLSTRING_MAX))) return false;
		if (! CabbageUtility.isNumeric(urlString)) return false;
		if (! CabbageUtility.isInBounds(urlString.substring(X_STARTPOS, X_ENDPOS),X_MIN,X_MAX)) return false;
		if (! CabbageUtility.isInBounds(urlString.substring(Y_STARTPOS, Y_ENDPOS),Y_MIN,Y_MAX)) return false;
		if (! isValidZParameter(urlString.substring(Z_STARTPOS, Z_ENDPOS),Z_MIN,Z_MAX)) return false;
		if (! validateCheckpoint(urlString)) return false;
		return true;
	}

	private boolean validateCheckpoint(String urlString) throws SQLException {
		cupcake = new Cupcake();
		if(!cupcake.checkCheckpointExists(urlString)) return false;
		return true;
	}

	// Checks that the numbers contained in a Z string parameter are within a given range.
	// A valid z parameter can be 00 or have a 0 on the left or right - 
	// but cannot contain non zeros on both sides.
	private  boolean isValidZParameter(String string, int min, int max) {
		if (!(CabbageUtility.checkLengthOfString(string,Z_MINCHARS,Z_MINCHARS))) return false;

		char str1 = string.charAt(0);
		char str2 = string.charAt(1);

		int num1 = Character.getNumericValue(str1);
		int num2 = Character.getNumericValue(str2);

		if (num1 == num2) {
			if (num1 != 0) return false;
		}

		if (!(num1 >= min & num1 <= max)) return false;
		if (!(num2 >= min & num2 <= max)) return false;

		return true;
	}

}
