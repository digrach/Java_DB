package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import cabage.model.entities.CheckinBean;
import cabbage.database.utilities.DataBaseConnection;

/**
 * @author rachaelcolley
 *
 */

public class CheckinOps {

	private DataBaseConnection db;


	public CheckinOps() {
		db	= new DataBaseConnection();
	}

	/**
	 * Returns a list of CheckinBean for all checkins
	 * or an empty list if no checkins are found.
	 * @return List<CheckinBean> - list of CheckinBean
	 * @throws SQLException
	 */
	public List<CheckinBean> getAllCheckins() throws SQLException {
		return getCheckin("",0);

	}

	/**
	 * Returns a CheckinBean for a given checkin.
	 * Returns null if not found.
	 * @param checkin_id - the int checkin_id to get.
	 * @return CheckinBean - a CheckinBean
	 * @throws SQLException
	 */
	public CheckinBean getCheckinByID(int checkin_id) throws SQLException {
		CheckinBean cb = null;
		List<CheckinBean> cbList = getCheckin(" WHERE c.checkin_id=",checkin_id);
		cb = cbList.get(0);
		return cb;

	}

	/**
	 * Returns a list of CheckinBean for all, or a particular checkin(s)
	 * or an empty list if no checkins are found.
	 * @param whereCriteria - if passed, used to create WHERE criteria.
	 * @param checkin_id - the int checkin_id for the WHERE criteria.
	 * @return List<CheckinBean> - list of CheckinBean
	 * @throws SQLException
	 */
	private List<CheckinBean> getCheckin(String whereCriteria, int checkin_id) throws SQLException {
		String sqlString = "SELECT cm.name,l.urlstring,c.mytimestamp,c.checkin_id " +
				"FROM collectiblemaster cm " +
				"INNER JOIN livecollectible lc " +
				"ON cm.collectiblemaster_id=lc.collectiblemaster_id " +
				"INNER JOIN location l " +
				"ON lc.location_id=l.location_id " + 
				"INNER JOIN checkin c " +
				"ON l.urlstring=c.urlstring";

		if (!whereCriteria.equals("")) {
			sqlString += whereCriteria;
			sqlString += Integer.toString(checkin_id);
			sqlString += " group by c.checkin_id";
		} else {
			sqlString += " group by c.checkin_id";
		}

		System.out.println(sqlString);

		List<CheckinBean> cbList = new ArrayList<CheckinBean>();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement(sqlString);

		preparedStatement.execute();
		ResultSet rs = preparedStatement.getResultSet();

		cbList = getCheckinBean(rs);
		con.close();

		return cbList;

	}

	/**
	 * Returns a list of CheckinBean from a ResultSet
	 * or an empty list if no records are found.
	 * @param rs - the ResultSet to use.
	 * @return List<CheckinBean> - list of CheckinBean
	 * @throws SQLException
	 */
	private List<CheckinBean> getCheckinBean(ResultSet rs) throws SQLException {
		List<CheckinBean> cbList = new ArrayList<CheckinBean>();
		while(rs.next()) {
			String collectibleName = rs.getString("cm.name");
			String uString = rs.getString("l.urlString");
			Timestamp ts = localiseTimestamp(rs.getTimestamp("mytimestamp"));
			int id = rs.getInt("c.checkin_id");
			CheckinBean cb = new CheckinBean(id,collectibleName,uString,ts);
			cbList.add(cb);
		}
		return cbList;
	}

	/**
	 * Returns a Timestamp object in local time.
	 * @param ts - the Timestamp to localise.
	 * @return Timestamp - a Timestamp object.
	 * @throws SQLException
	 */
	private Timestamp localiseTimestamp(Timestamp ts) {
		// Declare the Locale (of the mysql instance).
		Locale remoteLocal = new Locale("en_US");
		// Declare the TimeZone (of the mysql instance);
		TimeZone remoteTimeZone = TimeZone.getTimeZone("EST");
		// Get a Calendar object which is set to the above.
		Calendar remoteCal = Calendar.getInstance(remoteTimeZone, remoteLocal);
		// Set Calendar time using the timestamp.
		remoteCal.setTime(ts);
		// Add 10 from Calendar hours (EST to Australia/Melbourne); 
		remoteCal.add(Calendar.HOUR, +10);

		Timestamp newTs = new Timestamp(remoteCal.getTimeInMillis());

		return newTs;
	}
	
	public JSONArray getCheckinBeanJSONArray2() throws SQLException, JSONException {

		JSONArray jArray = null;

		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("SELECT cm.name,l.urlstring,c.mytimestamp " +
						"FROM collectiblemaster cm " +
						"INNER JOIN livecollectible lc " +
						"ON cm.collectiblemaster_id=lc.collectiblemaster_id " +
						"INNER JOIN location l " +
						"ON lc.location_id=l.location_id " + 
						"INNER JOIN checkin c " +
						"ON l.urlstring=c.urlstring");
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getResultSet();
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			if(rs.next()) {
				return makeJSONArray2(rsmd,rs);
			}
		} finally {
			con.close();
		}
		return null;
	}
	
	private JSONArray makeJSONArray2(ResultSetMetaData rsmd, ResultSet rs) throws SQLException, JSONException {
		JSONArray jArray = new JSONArray();

		while(rs.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			for (int i=1; i<numColumns+1; i++) {
				String colname = rsmd.getColumnName(i);
				String column_name = stripColumnName(colname);

				if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
					obj.put(column_name, rs.getArray(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
					obj.put(column_name, rs.getInt(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
					obj.put(column_name, rs.getBoolean(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
					obj.put(column_name, rs.getBlob(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
					obj.put(column_name, rs.getDouble(colname)); 
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
					obj.put(column_name, rs.getFloat(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
					obj.put(column_name, rs.getInt(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
					obj.put(column_name, rs.getNString(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
					obj.put(column_name, rs.getString(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
					obj.put(column_name, rs.getInt(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
					obj.put(column_name, rs.getInt(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
					obj.put(column_name, rs.getDate(colname));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
					Timestamp ts = rs.getTimestamp(colname);
					Calendar c = Calendar.getInstance();
					
					c.setTime(ts);
					c.add(Calendar.HOUR, +10);
					Date date = c.getTime();             
					SimpleDateFormat checkinDate = new SimpleDateFormat("EEE, MMM d, ''yy");
					String cDate = checkinDate.format(date); 
					System.out.println(cDate);
					SimpleDateFormat checkinTime = new SimpleDateFormat("h:mm a");
					String cTime = checkinTime.format(date); 
					//obj.put("time", rs.getTimestamp(colname));   
//					obj.put("checkinTime", checkinTime);
//					obj.put("checkinDate", checkinDate);
					obj.put("checkinTime", cTime);
					obj.put("checkinDate", cDate);
				}
				else{
					obj.put(column_name, rs.getObject(colname));
				}
			}

			jArray.put(obj);
		}

		return jArray;
	}

	private String stripColumnName(String s) {
		String str = null;
		int startPos = s.indexOf(".");
		str = s.substring(startPos + 1);
		System.out.println(str);


		return str;
	}


}
