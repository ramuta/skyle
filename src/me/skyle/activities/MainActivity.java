package me.skyle.activities;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.skyle.R;
import me.skyle.R.layout;
import me.skyle.R.menu;
import me.skyle.objects.Item;
import me.skyle.other.CameraHelper;
import me.skyle.other.GalleryHelper;
import me.skyle.other.SkyleDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "MainActivity";
	
	// actionbar
	private ActionBar actionBar;
	
	// UI
	TextView hello;
	
	/*
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	*/
	
	// galerry/camera
	private static int IMAGE_FLAG = 0; // gallery: 1, camera: 2
	private Uri fileUri;
	private final static int CAMERA_GALLERY_REQUEST_CODE = 123;
	public static final String PIC_PATH = "getpicpath";
	
	// database
	SkyleDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // actionbar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        db = new SkyleDatabase(MainActivity.this);
        
        /*
        // instantiate image loader
  		imageLoader = ImageLoader.getInstance();
  		options = new DisplayImageOptions.Builder()
 		.showStubImage(R.drawable.ic_launcher)
 		.cacheInMemory()
 		.cacheOnDisc()
 		.build();
  		imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
  		*/
        
        hello = (TextView)findViewById(R.id.main_hello);
        
        // Outfit tab
        ActionBar.Tab outfit = getSupportActionBar().newTab();
        outfit.setText("Outfit");
        outfit.setTabListener(new ActionBar.TabListener(){
        	@Override
        	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		hello.setText("Hello outfit");
        	}

        	@Override
        	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onTabReselected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		
        	}
        });
        
        // closet tab
        ActionBar.Tab closet = getSupportActionBar().newTab();
        closet.setText("Closet");
        closet.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
				db.open();
				ArrayList<Item> items = db.getItems();
				db.close();
				
				try {
					hello.setText("Hello closet: "+items.get(0).getImagePath());
				} catch (Exception e) {
					hello.setText("PRAZNO");
				}
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		});
        
        // shop tab
        ActionBar.Tab shop = getSupportActionBar().newTab();
        shop.setText("Shop");
        shop.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				hello.setText("Hello shop");
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		});
        
        getSupportActionBar().addTab(outfit);
        getSupportActionBar().addTab(closet);
        getSupportActionBar().addTab(shop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
        	Log.i(TAG, "settings");
        	return true;
        case R.id.menu_camera:
        	openGetImageDialogBox();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
    	if (reqCode == CAMERA_GALLERY_REQUEST_CODE) {
    		if (IMAGE_FLAG == 1) { // gallery
    			Uri getData = data.getData();   		
				String picPath = GalleryHelper.getRealPathFromURI(getApplicationContext(), getData);
				GalleryHelper.setImagePath(picPath);
				goToAddItemActivity(picPath);				
    		}
    		else if (IMAGE_FLAG == 2) { // camera intent
    			String photoPath = CameraHelper.getPhotoPath();
    			goToAddItemActivity(photoPath);
    		}
    	}
    }
    
    /** Go to AddItemActivity. */
    private void goToAddItemActivity(String picPath) {
    	Intent addItemIntent = new Intent(this, AddItemActivity.class);
    	addItemIntent.putExtra(PIC_PATH, picPath);
    	startActivity(addItemIntent);
    }
    
    /** Opens a dialog box to choose between Camera and Gallery. */
    private void openGetImageDialogBox() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle("Select image");
		alert.setMessage("Take a picture with a camera or select it from your gallery.");
		alert.setNegativeButton("Gallery", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				IMAGE_FLAG = 1;
				Log.i("TAG", "Gremo v gallery");
				goToGallery();
			}});
		alert.setPositiveButton("Camera", new DialogInterface.OnClickListener() {				
			public void onClick(DialogInterface dialog, int which) {
				IMAGE_FLAG = 2;
				Log.i("TAG", "Odpri fotoaparat");
				goToIntentCamera();
			}
		}).show();
    }
    
    /** Intent to open the gallery. */
    private void goToGallery() {
    	Intent intentGallery = new Intent();
    	intentGallery.setType("image/*");
    	intentGallery.setAction(Intent.ACTION_GET_CONTENT);
    	startActivityForResult(Intent.createChooser(intentGallery, "Select image"), CAMERA_GALLERY_REQUEST_CODE);
    }
    
    /** Intent to open original Android Camera app. */
    private void goToIntentCamera() {
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
    	fileUri = CameraHelper.getOutputMediaFileUri(CameraHelper.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
    	startActivityForResult(intent, CAMERA_GALLERY_REQUEST_CODE);
    }
}
