package cabage.model.logic;

import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import cabbage.controller.Cupcake;

public class CheckinInfo {
	
	private Cupcake cupcake;
	
	public CheckinInfo() {
		
	}
	
	public JSONArray getCheckinBeanJSONArray2() throws SQLException, JSONException {
		cupcake = new Cupcake();
		return cupcake.getCheckinBeanJSONArray2();
	}

}
