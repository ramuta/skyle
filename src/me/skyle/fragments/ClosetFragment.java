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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class ClosetFragment extends SherlockFragment {
	private static final String TAG = "ClosetFragment";
	private ArrayList<Item> items = new ArrayList<Item>();
	private ItemHelper itemHelper;
	
	//UI
	private ItemsAdapter adapter;
	private ListView list;
	
	@Override
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_closet, container, false);
		
		itemHelper = new ItemHelper(getActivity());
		
		TextView text = (TextView)view.findViewById(R.id.closet_text);
		text.setText("EVO SPREMENJENO :)"+System.currentTimeMillis());
		
		list = (ListView)view.findViewById(R.id.closet_list);
		
		// get Items ArrayList from ItemHelper.
		items.clear();
		items = itemHelper.getItems();

        // Inflate the layout for this fragment
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		adapter = new ItemsAdapter(getActivity(), R.layout.fragment_list_item, items);
		list.setAdapter(adapter);
	}
}
