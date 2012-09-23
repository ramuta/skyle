package me.skyle.other;

import java.util.ArrayList;

import me.skyle.data.SkyleConstants;
import me.skyle.objects.Item;

import android.content.Context;

public class ItemHelper {
	private static final String TAG = "ItemHelper";
	private Context context;
	private SkyleDatabase db;
	
	// data
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static ArrayList<Item> itemsTop = new ArrayList<Item>();
	private static ArrayList<Item> itemsBottom = new ArrayList<Item>();
	private static ArrayList<Item> itemsShoes = new ArrayList<Item>();
	private static ArrayList<Item> itemsAccessories = new ArrayList<Item>();
	
	public ItemHelper(Context context) {
		this.context = context;
		db = new SkyleDatabase(context);
	}
	
	public void init() {
		items.clear();
		itemsAccessories.clear();
		itemsBottom.clear();
		itemsShoes.clear();
		itemsTop.clear();
		db.open();
		setItems(db.getItems());
		db.close();
		sortByItem();
	}
	
	public void sortByItem() {
		for (int i = 0; i < items.size(); i++) {
			Item sortItem = items.get(i);
			if (sortItem.getType().equals(SkyleConstants.TYPE_TOP)) {
				getItemsTop().add(sortItem);
			} else if (sortItem.getType().equals(SkyleConstants.TYPE_BOTTOM)) {
				getItemsBottom().add(sortItem);
			} else if (sortItem.getType().equals(SkyleConstants.TYPE_SHOES)) {
				getItemsShoes().add(sortItem);
			} else if (sortItem.getType().equals(SkyleConstants.TYPE_ACCESSORIES)) {
				getItemsAccessories().add(sortItem);
			}
		}
	}

	/** Get ArrayList of clothes in your Skyle closet.
	 * 
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/** Set ArrayList of clothes in your Skyle closet.
	 * 
	 * @param items the items to set
	 */
	private static void setItems(ArrayList<Item> items) {
		ItemHelper.items = items;
	}

	/**
	 * @return the itemsTop
	 */
	public ArrayList<Item> getItemsTop() {
		return itemsTop;
	}

	/**
	 * @param itemsTop the itemsTop to set
	 */
	private static void setItemsTop(ArrayList<Item> itemsTop) {
		ItemHelper.itemsTop = itemsTop;
	}

	/**
	 * @return the itemsBottom
	 */
	public ArrayList<Item> getItemsBottom() {
		return itemsBottom;
	}

	/**
	 * @param itemsBottom the itemsBottom to set
	 */
	private static void setItemsBottom(ArrayList<Item> itemsBottom) {
		ItemHelper.itemsBottom = itemsBottom;
	}

	/**
	 * @return the itemsShoes
	 */
	public ArrayList<Item> getItemsShoes() {
		return itemsShoes;
	}

	/**
	 * @param itemsShoes the itemsShoes to set
	 */
	private static void setItemsShoes(ArrayList<Item> itemsShoes) {
		ItemHelper.itemsShoes = itemsShoes;
	}

	/**
	 * @return the itemsAccessories
	 */
	public ArrayList<Item> getItemsAccessories() {
		return itemsAccessories;
	}

	/**
	 * @param itemsAccessories the itemsAccessories to set
	 */
	private static void setItemsAccessories(ArrayList<Item> itemsAccessories) {
		ItemHelper.itemsAccessories = itemsAccessories;
	}
}
