package me.skyle.other;

import java.util.ArrayList;

import me.skyle.data.SkyleConstants;
import me.skyle.objects.Item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class SkyleDatabase {
	private static final String TAG = "SkyleDatabase";
	
	// db variables
	private SQLiteDatabase db;
	private DatabaseHelper helper;
	
	// other
	private Context context;
	
	// items
	private static ArrayList<Item> items = new ArrayList<Item>(); // user's items
	
	private String[] allColumns = { SkyleConstants.KEY_ID, 
			SkyleConstants.ITEMS_TYPE,  
			SkyleConstants.DATE_NAME, 
			SkyleConstants.ITEMS_PATH};

	public SkyleDatabase(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context, SkyleConstants.DB_NAME, null, SkyleConstants.DB_VERSION);
	}
	
	/** Open the database. */
	public void open() {
		try {
			db = helper.getWritableDatabase();
		} catch (SQLiteException e) {
			Log.e(TAG, "Open database ex: "+e);
			db = helper.getReadableDatabase();
		}
	}
	
	/** Close the database. */
	public void close() {
		db.close();
	}
	
	/** Insert item with type and image path. */
	public long insertItem(String type, String imagePath) {
		try {
			ContentValues values = new ContentValues();
			values.put(SkyleConstants.ITEMS_TYPE, type);
			values.put(SkyleConstants.ITEMS_PATH, imagePath);
			values.put(SkyleConstants.DATE_NAME, java.lang.System.currentTimeMillis());
			return db.insert(SkyleConstants.TABLE_ITEMS, null, values); // tukaj se dobi ID
		} catch (Exception e) {
			Log.e(TAG, "insert item ex: "+e);
			return -1;
		}
	}
	
	/** Get user's items array. */
	public ArrayList<Item> getItems() {
		items.clear();
		Cursor cursor = db.query(SkyleConstants.TABLE_ITEMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			String type = cursor.getString(cursor.getColumnIndex(SkyleConstants.ITEMS_TYPE));
			String date = cursor.getString(cursor.getColumnIndex(SkyleConstants.DATE_NAME));
			String path = cursor.getString(cursor.getColumnIndex(SkyleConstants.ITEMS_PATH));
			
			Log.i(TAG, "elements: "+type+", "+path);
			
			Item item = new Item(type, date, path);			
			items.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		Log.i(TAG, "arraylist: "+items.toString());		
		return items;
	}
	
	/** Insert item id and weather id into WeatherItems table. */
	public long insertWeatherItems(long itemID, int weatherType) {
		try {
			int item2ID = (int) itemID;
			ContentValues values2 = new ContentValues();
			values2.put(SkyleConstants.WEATHER_ITEMS_ITEM, item2ID);
			values2.put(SkyleConstants.WEATHER_ITEMS_WEATHER, weatherType);
			return db.insert(SkyleConstants.TABLE_WEATHER_ITEMS, null, values2);
		} catch (Exception e) {
			Log.e(TAG, "insert WeatherItems ex: "+e);
			return -1;
		}
	}
}
