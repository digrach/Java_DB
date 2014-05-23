package cabage.model.entities;

import java.sql.Timestamp;

public class CheckinBean {
	
	private int checkin_id;
	private String name;
	private String urlstring;
	private Timestamp mytimestamp;
	
	public CheckinBean() {
		
	}
	
	public CheckinBean(int checkin_id, String name, String urlstring,
			Timestamp mytimestamp) {
		this.checkin_id = checkin_id;
		this.name = name;
		this.urlstring = urlstring;
		this.mytimestamp = mytimestamp;
	}
	
	public int getCheckin_id() {
		return checkin_id;
	}
	public void setCheckin_id(int checkin_id) {
		this.checkin_id = checkin_id;
	}
	public String getUrlstring() {
		return urlstring;
	}
	public void setUrlstring(String urlstring) {
		this.urlstring = urlstring;
	}
	public Timestamp getMytimestamp() {
		return mytimestamp;
	}
	public void setMytimestamp(Timestamp mytimestamp) {
		this.mytimestamp = mytimestamp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
