package cabage.model.logic;

import java.sql.SQLException;

import cabbage.controller.Cupcake;
import cabbage.utilities.CabbageUtility;

public class CollectibleCreation {

	private final int NAME_MIN=3;
	private final int NAME_MAX=100;
	private final int DESCRIPTION_MIN=0;
	private final int DESCRIPTION_MAX=500;
	private final int IMAGEFILENAME_MIN=0;
	private final int IMAGEFILENAME_MAX=100;

	private Cupcake dbc;

	public CollectibleCreation() {

	}

	public int createFaculty() throws SQLException {
		dbc = new Cupcake();
		return dbc.insertNewFaculty();
	}

	public int createSkill(int facultyid) throws SQLException {
		dbc = new Cupcake();
		if (!dbc.checkFacultyidExists(facultyid)) {
			System.out.println(facultyid);
			System.out.println("x");
			return 0;
		}
		return dbc.insertNewSkill(facultyid);
	}

	public int createItem(int facultyid,int skillid) throws SQLException {
		dbc = new Cupcake();
		if (!dbc.checkFacultyidExists(facultyid)) return 0;
		if (!dbc.checkSkillidExists(skillid)) return 0;
		return dbc.insertNewItem(facultyid,skillid);
	}

	public int createCollectible(int itemid, int skillid, int facultyid,
			String name, int collectiblecategoryid, String description, String imagefilename) throws SQLException {

		dbc = new Cupcake();
		if (!CabbageUtility.checkLengthOfString(name, NAME_MIN, NAME_MAX)) return 0;
		if (!CabbageUtility.checkLengthOfString(description, DESCRIPTION_MIN, DESCRIPTION_MAX)) return 0;
		if (!CabbageUtility.checkLengthOfString(imagefilename, IMAGEFILENAME_MIN, IMAGEFILENAME_MAX)) return 0;
		if (CabbageUtility.containsNaughtyWords(name)) return 0; //
		if (CabbageUtility.containsNaughtyWords(description)) return 0; //
		if (CabbageUtility.containsNaughtyWords(imagefilename)) return 0; //
		if (dbc.checkNameExists(name)) return 0;
		if (!validateIDs(collectiblecategoryid, itemid,  skillid,  facultyid)) return 0;
		
		return dbc.insertNewCollectible(itemid, skillid, facultyid, name, collectiblecategoryid, description, imagefilename);
	}

	private boolean validateIDs(int collectibleCategoryID, int itemid, int skillid, int facultyid) throws SQLException {

		// MUST ALWAYS HAVE A FACULTYID > 0.
		if (facultyid == 0) return false;

		if (itemid > 0) {
			if(!validateItemID(itemid)) return false;
		}

		if (skillid > 0) {
			if(!validateSkillID(skillid)) return false;
		}

		if (facultyid > 0) {
			if(!validateFacultyID(facultyid)) return false;
		}

		if (!validateCategoryID(collectibleCategoryID, itemid, skillid, facultyid)) return false;

		return true;

	}

	private boolean validateItemID(int itemid) throws SQLException {
		dbc = new Cupcake();
		if (itemid > 0) {
			if(dbc.checkItemidExists(itemid)) return true;
		}
		return false;
	}

	private boolean validateSkillID(int skillid) throws SQLException {
		dbc = new Cupcake();
		if (skillid > 0) {
			if(dbc.checkSkillidExists(skillid)) return true;
		}
		return false;
	}

	private boolean validateFacultyID(int facultyid) throws SQLException {
		dbc = new Cupcake();
		if (facultyid > 0) {
			if(dbc.checkFacultyidExists(facultyid)) return true;
		}
		return false;
	}

	private boolean validateCategoryID(int collectibleCategoryID, int itemid, int skillid, int facultyid) throws SQLException {
		dbc = new Cupcake();

		// If an item, the cat must be the same as item cat.
		if (itemid > 0) {
			if (collectibleCategoryID == dbc.getCollectibleCategoryid("item")) {
				return true;
			} else {
				return false;
			}
		}

		// If an skill, the cat must be the same as skill cat.
		if (skillid > 0) {
			if (collectibleCategoryID == dbc.getCollectibleCategoryid("skill")) {
				return true;
			} else {
				return false;
			}
		}

		// If none of the above, the cat must be the same as faculty cat.
		if (facultyid > 0) {
			if (collectibleCategoryID == dbc.getCollectibleCategoryid("faculty")) {
				return true;
			} else {
				return false;
			}
		}

		return false;

	}

}
