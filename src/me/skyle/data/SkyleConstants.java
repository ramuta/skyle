package me.skyle.data;

public class SkyleConstants {
	
	/** DATABASE CONSTANTS */
	public static final String DB_NAME = "skyledatabase";
	public static int DB_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String DATE_NAME = "recorddate";
	
	/** Items table */
	public static final String TABLE_ITEMS = "items";	
	public static final String ITEMS_TYPE = "itemstype";
	public static final String ITEMS_PATH = "itemspath";
	
	/** Weather table */
	public static final String TABLE_WEATHER = "weather";
	public static final String WEATHER_TYPE = "weathertype";
	
	/** WeatherItems table */
	public static final String TABLE_WEATHER_ITEMS = "weatheritems";
	public static final String WEATHER_ITEMS_WEATHER = "weatheritemsweather";
	public static final String WEATHER_ITEMS_ITEM = "weatheritemsitem";
	
	/** Item types */
	public static final String TYPE_TOP = "top";
	public static final String TYPE_BOTTOM = "bottom";
	public static final String TYPE_SHOES = "shoes";
	public static final String TYPE_ACCESSORIES = "accessories";
}
