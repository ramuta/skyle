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
import me.skyle.fragments.ClosetFragment;
import me.skyle.fragments.OutfitFragment;
import me.skyle.fragments.ShopFragment;
import me.skyle.objects.Item;
import me.skyle.other.CameraHelper;
import me.skyle.other.GalleryHelper;
import me.skyle.other.ItemHelper;
import me.skyle.other.SkyleDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "MainActivity";
	
	// actionbar
	private ActionBar actionBar;
	
	// galerry/camera
	private static int IMAGE_FLAG; // gallery: 1, camera: 2
	private Uri fileUri;
	private final static int GALLERY_REQUEST_CODE = 111;
	private final static int CAMERA_REQUEST_CODE = 222;
	public static final String PIC_PATH = "getpicpath";
	
	// database
	//SkyleDatabase db;
	private ItemHelper itemHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // actionbar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //db = new SkyleDatabase(MainActivity.this);
        
        itemHelper = new ItemHelper(MainActivity.this);
		itemHelper.init(); // get items from the database
        
        // Outfit tab
        ActionBar.Tab outfit = getSupportActionBar().newTab();
        outfit.setText("Outfit");
        outfit.setTabListener(new ActionBar.TabListener(){
        	@Override
        	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        		Fragment outfitFragment = new OutfitFragment();
        		getSupportFragmentManager().beginTransaction().replace(R.id.container, outfitFragment).commit();
        	}

        	@Override
        	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

        	@Override
        	public void onTabReselected(Tab tab, FragmentTransaction ft) {}
        });
        
        // closet tab
        ActionBar.Tab closet = getSupportActionBar().newTab();
        closet.setText("Closet");
        closet.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				Fragment closetFragment = new ClosetFragment();
        		getSupportFragmentManager().beginTransaction().replace(R.id.container, closetFragment).commit();
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {}
		});
        
        // shop tab
        ActionBar.Tab shop = getSupportActionBar().newTab();
        shop.setText("Shop");
        shop.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				Fragment shopFragment = new ShopFragment();
        		getSupportFragmentManager().beginTransaction().replace(R.id.container, shopFragment).commit();
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {}
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
		try {
			if (reqCode == GALLERY_REQUEST_CODE) {
				Uri getData = data.getData(); 
				String picPath = GalleryHelper.getRealPathFromURI(getApplicationContext(), getData);
				GalleryHelper.setImagePath(picPath);
				goToAddItemActivity(picPath);				
			} else if (reqCode == CAMERA_REQUEST_CODE) {
				String photoPath = CameraHelper.getPhotoPath();
				goToAddItemActivity(photoPath);
			}
		} catch (Exception e) {
			Log.e(TAG, "No item chosen.");
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
    	startActivityForResult(Intent.createChooser(intentGallery, "Select image"), GALLERY_REQUEST_CODE);
    }
    
    /** Intent to open original Android Camera app. */
    private void goToIntentCamera() {
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
    	fileUri = CameraHelper.getOutputMediaFileUri(CameraHelper.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
    	startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	itemHelper.init(); // get items from the database
    }
}
