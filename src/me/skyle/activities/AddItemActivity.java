package me.skyle.activities;

import java.util.ArrayList;

import me.skyle.R;
import me.skyle.data.SkyleConstants;
import me.skyle.other.SkyleDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AddItemActivity extends SherlockActivity {
	private static final String TAG = "AddItemActivity";
	
	// ImageLoader
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	// UI
	private ActionBar actionBar;
	private ImageView itemImage;
	//private TextView addType;
	private Button insertButton;
	private Button addWeather;
	private Spinner clothingCategory;
	private ArrayAdapter<String> spinnerAdapter;
	
	// data
	private String imagePath;
	private final String[] weathers = {"Freezing", "Cold", "Warm", "Hot"};
	private ArrayList<String> selectedWeathers = new ArrayList<String>();
	private boolean[] selectWeath = {false, false, false, false};
	public static String ITEM_TYPE;
	private ArrayList<String> clothCat = new ArrayList<String>();
	
	// database
	private SkyleDatabase db;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        
        // actionbar
        actionBar = getSupportActionBar();
        
        // database
        db = new SkyleDatabase(AddItemActivity.this);
        
        // UI
        itemImage = (ImageView)findViewById(R.id.additem_imagethumb);
        //addType = (TextView)findViewById(R.id.select_type);
        insertButton = (Button)findViewById(R.id.additem_insertbutton);
        addWeather = (Button)findViewById(R.id.additem_addweather);
        clothingCategory = (Spinner)findViewById(R.id.additem_selecttype);
        
        // instantiate image loader
  		imageLoader = ImageLoader.getInstance();
  		options = new DisplayImageOptions.Builder()
 		.showStubImage(R.drawable.ic_launcher)
 		.cacheInMemory()
 		.cacheOnDisc()
 		.build();
  		imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
  		
  		// get Intent extras
  		Intent intent = getIntent();
		imagePath = intent.getStringExtra(MainActivity.PIC_PATH);
		Log.i(TAG, "imagePath: "+imagePath);
		imageLoader.displayImage("file://"+imagePath, itemImage, options);		
        
        // clothing category
        clothCat.add(SkyleConstants.TYPE_TOP);
        clothCat.add(SkyleConstants.TYPE_BOTTOM);
        clothCat.add(SkyleConstants.TYPE_SHOES);
        clothCat.add(SkyleConstants.TYPE_ACCESSORIES);
        
        spinnerAdapter = new ArrayAdapter<String>(AddItemActivity.this, android.R.layout.simple_spinner_item, clothCat);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);        
        clothingCategory.setAdapter(spinnerAdapter);
        
        // add weather button
        addWeather.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				weathersDialog();
			}
		});
		
        // insert button
		insertButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String getType = clothingCategory.getSelectedItem().toString();
				Log.i(TAG, "type: "+getType);
				insertItemIntoDb(getType);
				//ITEM_TYPE = "";
				//selectedWeathers.clear();
				AddItemActivity.this.finish();
			}
		});
		
		/*
		addType.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent itemTypeIntent = new Intent(AddItemActivity.this, ItemDialogActivity.class);
				startActivity(itemTypeIntent);
				
			}
		});
		*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
	}
	
	/** Open a dialog that let's user select multiple weather conditions for the item. */
	private void weathersDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
		builder.setTitle("Select weather conditions.");
		builder.setMultiChoiceItems(weathers, selectWeath, new DialogInterface.OnMultiChoiceClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				selectWeath[which] = isChecked;
				if (isChecked) {
					Log.i(TAG, "Selected "+weathers[which]);					
					selectedWeathers.add(weathers[which]);
				} else {
					Log.i(TAG, "Unselected "+weathers[which]);
					selectedWeathers.remove(weathers[which]);
				}				
			}
		}).show();
	}
	
	/** Insert item into database. */
	private void insertItemIntoDb(String type) {
		db.open();
		long itemIDlong = db.insertItem(type, imagePath);
		int itemIDint = (int) itemIDlong;
		Log.i(TAG, "item id: "+itemIDint);
		
		for (int x = 0; x < selectedWeathers.size(); x++) {
			int weatherID = getWeatherID(selectedWeathers.get(x))+1;
			Log.i(TAG, "weather id: "+weatherID);
			// insert into weather_items table
			db.insertWeatherItems(itemIDint, weatherID);
		}		
		db.close();		
	}
	
	/** Najdi ID za izbrani weather condition. */
	private int getWeatherID(String condition) {
		for (int y=0; y < weathers.length; y++) {
			if (weathers[y].equals(condition)) {
				return y;
			}
		}
		return 0;
	}
}
