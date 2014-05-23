package cabage.model.entities;

public class CollectibleMasterBean {

	private int	collectiblemaster_id;
	private int item_id;
	private int skill_id;
	private int faculty_id;
	private String name;
	private int collectiblecategory_id;
	private String description;
	private String imagefilename;
	
	public CollectibleMasterBean() {
		
	}
	
	public CollectibleMasterBean(int collectiblemaster_id, int item_id,
			int skill_id, int faculty_id, String name,
			int collectiblecategory_id, String description, String imagefilename) {
		
		this.collectiblemaster_id = collectiblemaster_id;
		this.item_id = item_id;
		this.skill_id = skill_id;
		this.faculty_id = faculty_id;
		this.name = name;
		this.collectiblecategory_id = collectiblecategory_id;
		this.description = description;
		this.imagefilename = imagefilename;
	}
	public int getCollectiblemaster_id() {
		return collectiblemaster_id;
	}
	public void setCollectiblemaster_id(int collectiblemaster_id) {
		this.collectiblemaster_id = collectiblemaster_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public int getSkill_id() {
		return skill_id;
	}
	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}
	public int getFaculty_id() {
		return faculty_id;
	}
	public void setFaculty_id(int faculty_id) {
		this.faculty_id = faculty_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCollectiblecategory_id() {
		return collectiblecategory_id;
	}
	public void setCollectiblecategory_id(int collectiblecategory_id) {
		this.collectiblecategory_id = collectiblecategory_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagefilename() {
		return imagefilename;
	}
	public void setImagefilename(String imagefilename) {
		this.imagefilename = imagefilename;
	}
	
	
	
	
	

}
