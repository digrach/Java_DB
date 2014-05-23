package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cabage.model.entities.CollectibleMasterBean;
import cabbage.database.utilities.DataBaseConnection;

public class CollectibleCreationOps {

	private DataBaseConnection db;

	
	public CollectibleCreationOps() {

	}
	
	/**
	 * Inserts a new collectible into collectiblemaster table. 
	 * @return id - the new id.
	 * @throws SQLException
	 */
	public int insertNewCollectible(int itemid, int skillid, int facultyid,
			String name, int collectiblecategoryid, String description, String imagefilename) throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into collectiblemaster (item_id, skill_id,faculty_id,name,collectiblecategory_id,description,imagefilename) values (?,?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, itemid);
		preparedStatement.setInt(2, skillid);
		preparedStatement.setInt(3, itemid);
		preparedStatement.setString(4, name);
		preparedStatement.setInt(5, collectiblecategoryid);
		preparedStatement.setString(6, description);
		preparedStatement.setString(7, imagefilename);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}
	
	/**
	 * Inserts a new faculty into faculties table. 
	 * @return id - the new id.
	 * @throws SQLException
	 */
	public int insertNewFaculty() throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into faculty (faculty_id) values (null)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}

	/**
	 * Inserts a new skill into skill table. 
	 * @param facultyid - the int facultyid that the skill belongs to.
	 * @return id - the new id.
	 * @throws SQLException
	 */
	public int insertNewSkill(int facultyid) throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into skill (faculty_id) values (?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, facultyid);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}
	
	
	/**
	 * Inserts a new item into item table. 
	 * @param facultyid - the int facultyid that the item's parent skill belongs to.
	 * @param skillid - the int skillid that the item belongs to.
	 * @return id - the new id.
	 * @throws SQLException
	 */
	public int insertNewItem(int facultyid, int skillid) throws SQLException {
		int returnid = 0;
		db = new DataBaseConnection();
		Connection con = db.getConnection();
		PreparedStatement preparedStatement = 
				con.prepareStatement("insert into item (faculty_id,skill_id) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, facultyid);
		preparedStatement.setInt(2, skillid);
		preparedStatement.execute();
		ResultSet rskey = preparedStatement.getGeneratedKeys();
		if (rskey.next()) {
			returnid = rskey.getInt(1);
		}
		con.close();
		return returnid;
	}

}
