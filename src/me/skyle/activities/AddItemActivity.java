package me.skyle.activities;

import me.skyle.R;
import me.skyle.other.SkyleDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	private TextView zacasniAddType;
	private Button insertButton;
	
	// image data
	private String imagePath;
	
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
        zacasniAddType = (TextView)findViewById(R.id.select_type);
        insertButton = (Button)findViewById(R.id.additem_insertbutton);
        
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
		
		insertButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String getType = zacasniAddType.getText().toString();
				insertItemIntoDb(getType);
			}
		});
		
		zacasniAddType.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent itemTypeIntent = new Intent(AddItemActivity.this, ItemDialogActivity.class);
				startActivity(itemTypeIntent);
			}
		});
	}
	
	/** Insert item into database. */
	private void insertItemIntoDb(String type) {
		db.open();
		db.insertItem(type, imagePath);
		db.close();
		AddItemActivity.this.finish();
	}
}
