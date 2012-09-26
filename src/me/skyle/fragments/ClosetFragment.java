package me.skyle.fragments;

import java.util.ArrayList;

import me.skyle.R;
import me.skyle.activities.MainActivity;
import me.skyle.adapters.ItemsAdapter;
import me.skyle.objects.Item;
import me.skyle.other.ItemHelper;
import me.skyle.other.SkyleDatabase;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.devsmart.android.ui.HorizontalListView;

public class ClosetFragment extends SherlockFragment {
	private static final String TAG = "ClosetFragment";
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
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		
	}
	
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_closet, container, false);
		
		galleryTop = (HorizontalListView)view.findViewById(R.id.closet_gallery_top);
		galleryBottom = (HorizontalListView)view.findViewById(R.id.closet_gallery_bottom);
		galleryShoes = (HorizontalListView)view.findViewById(R.id.closet_gallery_shoes);

        // Inflate the layout for this fragment
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		
		tops = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsTop);
		galleryTop.setAdapter(tops);
		
		bottoms = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsBottom);
		galleryBottom.setAdapter(bottoms);
		
		shoes = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, itemsShoes);
		galleryShoes.setAdapter(shoes);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		itemsShoes = itemHelper.getItemsShoes();
		itemsTop = itemHelper.getItemsTop();
		itemsBottom = itemHelper.getItemsBottom();
		itemsAccessories = itemHelper.getItemsAccessories();

		Log.i(TAG, "Shoes: "+itemsShoes+", tops: "+itemsTop);
		
		
	}
}
