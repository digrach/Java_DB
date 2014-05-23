package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import cabage.model.entities.CheckinBean;
import cabbage.database.utilities.DataBaseConnection;


public class CheckinUserOps {

	private DataBaseConnection db;

	public CheckinUserOps() {

	}

	/**
	 * Inserts a new checkin record and returns a CheckinBean for the new checkin.
	 * Returns null if not found.
	 * @param urlString - a Cabbage encoded urlString (xxxyyzz).
	 * @return CheckinBean - a CheckinBean
	 * @throws SQLException
	 */
	public CheckinBean checkin(String urlString) throws SQLException {
		db	= new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into checkin (urlstring) values (?)",Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, "superid=" + urlString);
		preparedStatement.execute();

		ResultSet rskey = preparedStatement.getGeneratedKeys();

		if (rskey.next()) {
			CheckinOps checkinOps = new CheckinOps();
			return checkinOps.getCheckinByID(rskey.getInt(1));
		}

		con.close();
		return null;
	}

}
