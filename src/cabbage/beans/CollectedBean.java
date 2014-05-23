package cabbage.beans;

public class CollectedBean {
	
	private int playerid;
	private int livecollectibleid;
	private int locationid;
	private int value;
	private String name;
	private String description;
	private String imagefilename;
	private int collectiblecategoryid;
	private String collectiblecategoryname;
	
	

	public CollectedBean(int playerid, int livecollectibleid, int locationid,
			int value, String name, String description, String imagefilename,
			int collectiblecategoryid, String collectiblecategoryname) {
		super();
		this.playerid = playerid;
		this.livecollectibleid = livecollectibleid;
		this.locationid = locationid;
		this.value = value;
		this.name = name;
		this.description = description;
		this.imagefilename = imagefilename;
		this.collectiblecategoryid = collectiblecategoryid;
		this.collectiblecategoryname = collectiblecategoryname;
	}



	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public int getLivecollectibleid() {
		return livecollectibleid;
	}

	public void setLivecollectibleid(int livecollectibleid) {
		this.livecollectibleid = livecollectibleid;
	}

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getCollectiblecategoryid() {
		return collectiblecategoryid;
	}

	public void setCollectiblecategoryid(int collectiblecategoryid) {
		this.collectiblecategoryid = collectiblecategoryid;
	}

	public String getCollectiblecategoryname() {
		return collectiblecategoryname;
	}

	public void setCollectiblecategoryname(String collectiblecategoryname) {
		this.collectiblecategoryname = collectiblecategoryname;
	}
	
	
	
	
	
	
}
