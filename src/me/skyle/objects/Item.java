package me.skyle.objects;

public class Item {
	private String ID;
	private String type;
	private String recordDate;
	private String imagePath;
	
	public Item (String ID, String type, String recordDate, String imagePath) {
		this.setID(ID);
		this.setType(type);
		this.setRecordDate(recordDate);
		this.setImagePath(imagePath);
	}

	/** Returns the type of this clothing item.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/** Returns the date at which the item was put into database.
	 * 
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	/** Returns the item picture's path in gallery or on web.
	 * 
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
}
