package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cabage.model.entities.CollectibleMasterBean;
import cabbage.database.utilities.DataBaseConnection;

/**
 * @author rachaelcolley
 *
 */
/**
 * @author rachaelcolley
 *
 */
public class CollectibleOps {

	private DataBaseConnection db;

	public CollectibleOps() {

	}	
	
	/**
	 * Returns CollectibleMasterBean with exact name match. Returns null if an exact match cannot be found.
	 * @param name - the String collectible name
	 * @return CollectibleMasterBean - mirrors the collectiblemaster table
	 * @throws SQLException
	 */
	public CollectibleMasterBean getCollectibleMaster(String name) throws SQLException {
		CollectibleMasterBean cmb = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE name=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		List<CollectibleMasterBean> cmbList = populateCollectibleBean(rs);
		if (cmbList != null) {
			cmb = cmbList.get(0);
		}
		conn.close();
		return cmb;
	}
	
	/**
	 * Returns CollectibleMasterBean with exact id match. Returns null if an exact match cannot be found.
	 * @param id - the int collectible id
	 * @return CollectibleMasterBean - mirrors the collectiblemaster table
	 * @throws SQLException
	 */
	public CollectibleMasterBean getCollectibleMaster(int id) throws SQLException {
		CollectibleMasterBean cmb = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE collectiblemaster_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<CollectibleMasterBean> cmbList = populateCollectibleBean(rs);
		if (cmbList != null) {
			cmb = cmbList.get(0);
		}
		conn.close();
		return cmb;
	}
	
	/**
	 * Returns a list of CollectibleMasterBean where the name field contains the search string.
	 * Returns null if a match cannot be found.
	 * @param searchString - the String name to search for.
	 * @return list of Collectible Master Beans - List of CollectibleMasterBean - each bean reflects the collectiblemaster table.
	 * @throws SQLException
	 */
	public List<CollectibleMasterBean> searchCollectibleMaster(String searchString) throws SQLException {
		List<CollectibleMasterBean> cmbList = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE name like lower(?)";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, "%" + searchString + "%");
		ResultSet rs = ps.executeQuery();
		cmbList = populateCollectibleBean(rs);
		
		conn.close();
		return cmbList;
	}
	
	/**
	 * Private utility method which populates a list of CollectibleMasterBean type from a result set.
	 * The connection object that the resultset belongs to must remain open during this operation.
	 * Returns null if the resultset is empty.
	 * @param rs - the ResultSet object to populate CollectibleMasterBean from.
	 * @return list of Collectible Master Beans - List of CollectibleMasterBean - each bean reflects the collectiblemaster table.
	 * @throws SQLException
	 */
	private List<CollectibleMasterBean> populateCollectibleBean(ResultSet rs) throws SQLException {
		List<CollectibleMasterBean> cmbList = null;
		while (rs.next()) {
			CollectibleMasterBean cmb = new CollectibleMasterBean();
			cmb.setCollectiblemaster_id(rs.getInt("collectiblemaster_id"));
			cmb.setFaculty_id(rs.getInt("faculty_id"));
			cmb.setSkill_id(rs.getInt("skill_id"));
			cmb.setItem_id(rs.getInt("item_id"));
			cmb.setName(rs.getString("name"));
			cmb.setCollectiblecategory_id(rs.getInt("collectiblecategory_id"));
			cmb.setDescription(rs.getString("description"));
			cmb.setImagefilename(rs.getString("imagefilename"));
			if (cmbList == null) {
				cmbList = new ArrayList<CollectibleMasterBean>();
			}
			cmbList.add(cmb);
		}
		return cmbList;
	}
	
	/**
	 * Returns id of a given collectible category. Returns 0 if an exact name match cannot be found.
	 * @param name - the string collectible category name.
	 * @return id - the int collectible category id
	 * @throws SQLException
	 */
	public int getCollectibleCategoryid(String name) throws SQLException {
		int id = 0;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblecategory WHERE name=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			id = rs.getInt(1);
			conn.close();
		}
		return id;
	}
	
	/**
	 * Returns id of a given faculty. Returns 0 if an exact name match cannot be found.
	 * The name must match a record that has been defined as faculty category.
	 * @param name - the string faculty name.
	 * @return id - the int faculty id
	 * @throws SQLException
	 */
	public int getFacultyid(String name) throws SQLException {
		int returnid = 0;
		int collectibleCategoryid = getCollectibleCategoryid("faculty");
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT faculty_id FROM collectiblemaster WHERE name=? AND collectiblecategory_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ps.setInt(2, collectibleCategoryid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			returnid = rs.getInt(1);
		}
		conn.close();
		return returnid;
	}

	/**
	 * Returns id of a given skill. Returns 0 if an exact name match cannot be found.
	 * The name must match a record that has been defined as skill category.
	 * @param name - the string skill name.
	 * @return id - the int skill id
	 * @throws SQLException
	 */
	public int getSkillid(String name) throws SQLException {
		int returnid = 0;
		int collectibleCategoryid = getCollectibleCategoryid("skill");
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT skill_id FROM collectiblemaster WHERE name=? AND collectiblecategory_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ps.setInt(2, collectibleCategoryid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			returnid = rs.getInt(1);
		}
		conn.close();
		return returnid;
	}

	/**
	 * Returns id of a given item. Returns 0 if an exact name match cannot be found.
	 * The name must match a record that has been defined as item category.
	 * @param name - the string item name.
	 * @return id - the int item id
	 * @throws SQLException
	 */
	public int getItemid(String name) throws SQLException {
		int returnid = 0;
		int collectibleCategoryid = getCollectibleCategoryid("item");
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT item_id FROM collectiblemaster WHERE name=? AND collectiblecategory_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ps.setInt(2, collectibleCategoryid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			returnid = rs.getInt(1);
		}
		conn.close();
		return returnid;
	}

	/**
	 * Returns true if the given facultyid exists.
	 * @param id - the int faculty id
	 * @return exists - true if the facultyid exists.
	 * @throws SQLException
	 */
	public boolean checkFacultyidExists(int id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM faculty WHERE faculty_id=?";
//		String sqlString = "SELECT * FROM collectiblemaster WHERE faculty_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			
			return true;
		}
		conn.close();
		System.out.println(id);
		System.out.println("op");
		return false;
	}

	/**
	 * Returns true if the given skillid exists.
	 * @param id - the int skill id
	 * @return exists - true if the skillid exists.
	 * @throws SQLException
	 */
	public boolean checkSkillidExists(int id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
//		String sqlString = "SELECT * FROM collectiblemaster WHERE skill_id=?";
		String sqlString = "SELECT * FROM skill WHERE skill_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}

	/**
	 * Returns true if the given itemid exists.
	 * @param id - the int item id
	 * @return exists - true if the itemid exists.
	 * @throws SQLException
	 */
	public boolean checkItemidExists(int id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
//		String sqlString = "SELECT * FROM collectiblemaster WHERE item_id=?";
		String sqlString = "SELECT * FROM item WHERE item_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}
	
	/**
	 * Returns true if the given collectible name exists.
	 * @param name - the string collectible name
	 * @return exists - true if the name exists.
	 * @throws SQLException
	 */
	public boolean checkNameExists(String name) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE name=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}
	
	/**
	 * Returns true if the given collectiblecategoryid exists.
	 * @param id - the int collectiblecategoryid
	 * @return exists - true if the id exists.
	 * @throws SQLException
	 */
	public boolean checkCollectibleCategoryExists(int id) throws SQLException {
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblecategory WHERE collectiblecategory_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}


	/**
	 * Returns a list of CollectibleMasterBean where the name field contains the search string.
	 * Returns null if a match cannot be found.
	 * @param searchString - the String name to search for.
	 * @return list of Collectible Master Beans - List of CollectibleMasterBean - each bean reflects the collectiblemaster table.
	 * @throws SQLException
	 */
	public List<CollectibleMasterBean> getCollectiblesByCategory(int categoryid) throws SQLException {
		List<CollectibleMasterBean> cmbList = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE collectiblecategory_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1,categoryid);
		ResultSet rs = ps.executeQuery();
		cmbList = populateCollectibleBean(rs);
		
		conn.close();
		return cmbList;
	}
	
	/**
	 * Returns true if a given collectiblemaster_id exists in the collectiblemaster table.
	 * @param collectiblemaster_id - the int id to search for.
	 * @return true or false - boolean.
	 * @throws SQLException
	 */
	public boolean checkCollectibleMasterExists(int collectiblemaster_id) throws SQLException{
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE collectiblemaster_id=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, collectiblemaster_id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			conn.close();
			return true;
		}
		conn.close();
		return false;
	}
	
	//
	
	/**
	 * Returns CollectibleMasterBean for all records in the collectiblemaster table. Returns null if no records can be found.
	 * @return List of CollectibleMasterBean - mirrors the collectiblemaster table
	 * @throws SQLException
	 */
	public List<CollectibleMasterBean> getAllCollectibleMasters() throws SQLException {
		List<CollectibleMasterBean> cmbList = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();
			cmbList =  populateCollectibleBean(rs);
			conn.close();
			return cmbList;
	}
	
	/**
	 * Returns CollectibleMasterBean for all records in the collectiblemaster table for a given collectiblecategory.
	 * Returns null if no records can be found.
	 * @return List of CollectibleMasterBean - mirrors the collectiblemaster table
	 * @throws SQLException
	 */
	public List<CollectibleMasterBean> getAllCollectibleMastersByCategory(int categoryid) throws SQLException {
		List<CollectibleMasterBean> cmbList = null;
		db = new DataBaseConnection();
		Connection conn = db.getConnection();
		String sqlString = "SELECT * FROM collectiblemaster WHERE category_id = ?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, categoryid);
		ResultSet rs = ps.executeQuery();
			cmbList =  populateCollectibleBean(rs);
			conn.close();
			return cmbList;
	}
}
