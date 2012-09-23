package me.skyle.adapters;

import java.util.ArrayList;

import me.skyle.R;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.skyle.objects.Item;
import me.skyle.other.SkyleDatabase;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ItemsAdapter extends ArrayAdapter<Item> {
	private static final String TAG = "ItemsAdapter";
	private LayoutInflater inflater;
	private ArrayList<Item> items;
	private SkyleDatabase db;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
		super(context, textViewResourceId, items);
		inflater = LayoutInflater.from(context);
		db = new SkyleDatabase(context.getApplicationContext());
		this.items = items;	
		imageLoader = ImageLoader.getInstance();
 		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .defaultDisplayImageOptions(options)
        .memoryCache(new WeakMemoryCache())
        .build();
 		imageLoader.init(config);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_list_item, null);
		}
		
		ImageView image = (ImageView)view.findViewById(R.id.fragment_list_item_image);
		
		Item newItem = items.get(position);
		
		imageLoader.displayImage("file://"+newItem.getImagePath(), image, options);
		
		return view;
	}
}
