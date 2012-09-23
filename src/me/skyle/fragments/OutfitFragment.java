package me.skyle.fragments;

import java.util.ArrayList;

import me.skyle.R;
import me.skyle.adapters.ItemsAdapter;
import me.skyle.objects.Item;
import me.skyle.other.ItemHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;

import com.actionbarsherlock.app.SherlockFragment;
import com.devsmart.android.ui.HorizontalListView;

public class OutfitFragment extends SherlockFragment {
	private static final String TAG = "OutfitFragment";
	private ItemHelper itemHelper = new ItemHelper(getActivity());
	
	// UI
	private HorizontalListView galleryTop;
	private HorizontalListView galleryBottom;
	private HorizontalListView galleryShoes;
	
	private ItemsAdapter tops;
	private ItemsAdapter bottoms;
	private ItemsAdapter shoes;
	private ItemsAdapter accessories;
	
	//data
	ArrayList<Item> itemsTop;
	ArrayList<Item> itemsBottom;
	ArrayList<Item> itemsShoes;
	ArrayList<Item> itemsAccessories;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_outfit, container, false);
		
		galleryTop = (HorizontalListView)view.findViewById(R.id.outfit_gallery_top);
		galleryBottom = (HorizontalListView)view.findViewById(R.id.outfit_gallery_bottom);
		galleryShoes = (HorizontalListView)view.findViewById(R.id.outfit_gallery_shoes);

        return view;
    }
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		itemsShoes = itemHelper.getItemsShoes();
		itemsTop = itemHelper.getItemsTop();
		itemsBottom = itemHelper.getItemsBottom();
		itemsAccessories = itemHelper.getItemsAccessories();
		Log.i(TAG, "Shoes: "+itemsShoes+", tops: "+itemsTop);
		
		tops = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsTop);
		galleryTop.setAdapter(tops);
		
		bottoms = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsBottom);
		galleryBottom.setAdapter(bottoms);
		
		shoes = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsShoes);
		galleryShoes.setAdapter(shoes);
	}
}
