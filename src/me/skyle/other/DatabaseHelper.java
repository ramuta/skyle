package me.skyle.other;

import me.skyle.data.SkyleConstants;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";
	
	/** SQL queries for creating tables. */
	private static final String CREATE_TABLE_ITEMS = "create table "+
			SkyleConstants.TABLE_ITEMS+" ("+
			SkyleConstants.KEY_ID+" integer primary key autoincrement, "+
			SkyleConstants.ITEMS_PATH+" text not null, "+
			SkyleConstants.ITEMS_TYPE+" text not null, "+
			SkyleConstants.DATE_NAME+" long);";
	
	private static final String CREATE_TABLE_WEATHER = "create table "+
			SkyleConstants.TABLE_WEATHER+" ("+
			SkyleConstants.KEY_ID+" integer primary key autoincrement, "+
			SkyleConstants.WEATHER_TYPE+" text not null);";
	
	private static final String CREATE_TABLE_WEATHER_ITEMS = "create table "+
			SkyleConstants.TABLE_WEATHER_ITEMS+" ("+
			SkyleConstants.WEATHER_ITEMS_ITEM+" integer not null, "+
			SkyleConstants.WEATHER_ITEMS_WEATHER+" integer not null);";

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// create tables
			db.execSQL(CREATE_TABLE_ITEMS);
			db.execSQL(CREATE_TABLE_WEATHER);
			db.execSQL(CREATE_TABLE_WEATHER_ITEMS);
			
			// Vstavi v tabelo weather vsa vremenska stanja (4)
			insertWeatherCategories(db);
			
		} catch (SQLException e) {
			Log.e(TAG, "Create table ex: "+e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Upgrading from version no."+oldVersion+" to version no."+newVersion+", which will destroy all old data.");		
		SkyleConstants.DB_VERSION = newVersion;
		// drop tables
		db.execSQL("drop table if exists "+SkyleConstants.TABLE_ITEMS);
		db.execSQL("drop table if exists "+SkyleConstants.TABLE_WEATHER);
		db.execSQL("drop table if exists "+SkyleConstants.TABLE_WEATHER_ITEMS);
		onCreate(db);
	}

	/** Insert four weather/temperature categories: freezing, cold, war, hot. */
	private void insertWeatherCategories(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(SkyleConstants.WEATHER_TYPE, "Freezing");
		values.put(SkyleConstants.WEATHER_TYPE, "Cold");
		values.put(SkyleConstants.WEATHER_TYPE, "Warm");
		values.put(SkyleConstants.WEATHER_TYPE, "Hot");
		db.insert(SkyleConstants.TABLE_WEATHER, null, values);
	}
}
