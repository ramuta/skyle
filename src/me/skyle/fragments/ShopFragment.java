package me.skyle.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;

import me.skyle.R;
import me.skyle.adapters.ItemsAdapter;
import me.skyle.objects.Item;
import me.skyle.other.ItemHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShopFragment extends SherlockFragment {
	private static final String TAG = "ShopFragment";
	private ArrayList<Item> items = new ArrayList<Item>();
	private ItemHelper itemHelper;
	
	//UI
	private ItemsAdapter adapter;
	private ListView list;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shop, container, false);
		
		itemHelper = new ItemHelper(getActivity());
		
		list = (ListView)view.findViewById(R.id.shop_list);
		
		// get Items ArrayList from ItemHelper.
		items.clear();
		items = itemHelper.getItems();

        // Inflate the layout for this fragment
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		adapter = new ItemsAdapter(getActivity(), R.layout.fragment_list_item, R.id.fragment_list_item_image, items);
		list.setAdapter(adapter);
	}
}
