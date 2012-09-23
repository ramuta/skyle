package me.skyle.other;

import java.util.ArrayList;

import me.skyle.objects.Item;

import android.content.Context;

public class ItemHelper {
	private static final String TAG = "ItemHelper";
	private Context context;
	private SkyleDatabase db;
	
	// data
	private static ArrayList<Item> items = new ArrayList<Item>();
	
	public ItemHelper(Context context) {
		this.context = context;
		db = new SkyleDatabase(context);
	}
	
	public void init() {
		items.clear();
		db.open();
		setItems(db.getItems());
		db.close();
	}

	/** Get ArrayList of clothes in your Skyle closet.
	 * 
	 * @return the items
	 */
	public static ArrayList<Item> getItems() {
		return items;
	}

	/** Set ArrayList of clothes in your Skyle closet.
	 * 
	 * @param items the items to set
	 */
	public static void setItems(ArrayList<Item> items) {
		ItemHelper.items = items;
	}
}
