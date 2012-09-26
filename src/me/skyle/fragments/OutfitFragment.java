package me.skyle.fragments;

import java.util.ArrayList;

import me.skyle.R;
import me.skyle.adapters.ItemsAdapter;
import me.skyle.objects.Item;
import me.skyle.other.ItemHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.devsmart.android.ui.HorizontalListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class OutfitFragment extends SherlockFragment {
	private static final String TAG = "OutfitFragment";
	private ItemHelper itemHelper = new ItemHelper(getActivity());
	private ImageLoader imageLoader;
    private DisplayImageOptions options;
	
	// UI
	private ListView gallery;
	private LinearLayout selectedTop;
	private LinearLayout selectedBottom;
	private LinearLayout selectedShoes;
	private ItemsAdapter galleryItemsAdapter;
	private ImageView imageTop1;
	private ImageView imageTop2;
	private ImageView imageTop3;
	private ImageView imageBottom1;
	private ImageView imageBottom2;
	private ImageView imageBottom3;
	private ImageView imageShoes1;
	private ImageView imageShoes2;
	private ImageView imageShoes3;
	
	//
	private ArrayList<Item> galleryItems;
	private ArrayList<Item> topItems = new ArrayList<Item>();
	private ArrayList<Item> bottomItems = new ArrayList<Item>();
	private ArrayList<Item> shoesItems = new ArrayList<Item>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_outfit, container, false);
		
		// initialize
		gallery = (ListView)view.findViewById(R.id.outfit_gallery);
		selectedTop = (LinearLayout)view.findViewById(R.id.outfit_selected_tops);
		selectedBottom = (LinearLayout)view.findViewById(R.id.outfit_selected_bottoms);
		selectedShoes = (LinearLayout)view.findViewById(R.id.outfit_selected_shoes);
		
		imageTop1 = (ImageView)view.findViewById(R.id.outfit_top_image1);
		imageTop2 = (ImageView)view.findViewById(R.id.outfit_top_image2);
		imageTop3 = (ImageView)view.findViewById(R.id.outfit_top_image3);
		
		imageBottom1 = (ImageView)view.findViewById(R.id.outfit_bottom_image1);
		imageBottom2 = (ImageView)view.findViewById(R.id.outfit_bottom_image2);
		imageBottom3 = (ImageView)view.findViewById(R.id.outfit_bottom_image3);
		
		imageShoes1 = (ImageView)view.findViewById(R.id.outfit_shoes_image1);
		imageShoes2 = (ImageView)view.findViewById(R.id.outfit_shoes_image2);
		imageShoes3 = (ImageView)view.findViewById(R.id.outfit_shoes_image3);

        return view;
    }
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);				
		
		galleryItems = itemHelper.getItems();		
		
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// adapter
		galleryItemsAdapter = new ItemsAdapter(getActivity(), R.layout.outfit_list_item, R.id.outfit_list_item_image, galleryItems);
		gallery.setAdapter(galleryItemsAdapter);
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String type = galleryItems.get(position).getType();
			Log.i(TAG, "type: "+type);
			String uri = galleryItems.get(position).getImagePath();
			//String imageID = galleryItems.get(position).getID();
			
			Item selItem = galleryItems.get(position);
			String itemID = selItem.getID();
			
			if (type.equals("top")) {
				topItems.add(selItem);
				Log.i(TAG, "itemID: "+itemID);
				if (imageTop1.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageTop1, options);
					imageTop1.setTag(itemID);
				} else if (imageTop2.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageTop2, options);
					imageTop2.setTag(itemID);
				} else if (imageTop3.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageTop3, options);
					imageTop3.setTag(itemID);
				} else {
					Log.i(TAG, "full Top gallery.");
				}
			} else if (type.equals("bottom")) {
				bottomItems.add(selItem);
				if (imageBottom1.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageBottom1, options);
					imageBottom1.setTag(itemID);
				} else if (imageBottom2.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageBottom2, options);
					imageBottom2.setTag(itemID);
				} else if (imageBottom3.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageBottom3, options);
					imageBottom3.setTag(itemID);
				} else {
					Log.i(TAG, "full Bottom gallery.");
				}
			} else if (type.equals("shoes") || type.equals("accessories")) {
				shoesItems.add(selItem);
				if (imageShoes1.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageShoes1, options);
					imageShoes1.setTag(itemID);
				} else if (imageShoes2.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageShoes2, options);
					imageShoes2.setTag(itemID);
				} else if (imageShoes3.getTag().equals("empty")) {
					imageLoader.displayImage("file://"+uri, imageShoes3, options);
					imageShoes3.setTag(itemID);
				} else {
					Log.i(TAG, "full Shoes gallery.");
				}
			}		
		}
		});
		
		imageTop1.setOnClickListener(topListener);
		imageTop2.setOnClickListener(topListener);
		imageTop3.setOnClickListener(topListener);
	
		imageBottom1.setOnClickListener(topListener);
		imageBottom2.setOnClickListener(topListener);
		imageBottom3.setOnClickListener(topListener);
		
		imageShoes1.setOnClickListener(topListener);
		imageShoes2.setOnClickListener(topListener);
		imageShoes3.setOnClickListener(topListener);
	}
	
	OnClickListener topListener = new OnClickListener() {					
		@Override
		public void onClick(View v) {
			// if clicked, remove from linlay
			String savedID = v.getTag().toString(); 
			if (!savedID.equals("empty")) {
				
				for (int m = 0; m < topItems.size(); m++) {
					Item oneItem = topItems.get(m);
					if (oneItem.getID().equals(savedID)) {
						topItems.remove(oneItem);
					}
				}
				
				for (int m = 0; m < bottomItems.size(); m++) {
					Item oneItem = bottomItems.get(m);
					if (oneItem.getID().equals(savedID)) {
						bottomItems.remove(oneItem);
					}
				}
				
				for (int m = 0; m < shoesItems.size(); m++) {
					Item oneItem = shoesItems.get(m);
					if (oneItem.getID().equals(savedID)) {
						shoesItems.remove(oneItem);
					}
				}

				imageLoader.displayImage("", (ImageView) v, options);
				v.setTag("empty");
			}
			Toast.makeText(getActivity(), "Choose image from sidebar.", Toast.LENGTH_SHORT).show();
		}
	};
}
