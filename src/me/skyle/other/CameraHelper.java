package me.skyle.other;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class CameraHelper {
	private static final String TAG = "CameraHelper";
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static String photoPath;
	private static final String DIRECTORY = "Skyle";
	private static final String PREFIX = "SKL_";
	
	/** Create a file Uri for saving an image or video */
    public static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
    	Log.i(TAG, "A je SD priklopljen? "+Environment.getExternalStorageState());

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY);
			
		// This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.e(DIRECTORY, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String timeStamp = "slika346";
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            		PREFIX+ timeStamp + ".jpg");
            System.out.println(mediaStorageDir.getPath() + File.separator + PREFIX+ timeStamp + ".jpg");
            String fajlPath = mediaStorageDir.getPath() + File.separator + PREFIX+ timeStamp + ".jpg";
            Log.i(TAG, "fajlPath: "+fajlPath);
            photoPath = fajlPath;
            
        } else {
            	return null;
        }
        // TODO: daj photoPath v LefemmeUser statièno metodo
        setPhotoPath("/mnt/sdcard/Pictures/"+DIRECTORY+"/"+PREFIX+ timeStamp + ".jpg");        
        return mediaFile;
    }

	/**
	 * @return the photoPath
	 */
	public static String getPhotoPath() {
		return photoPath;
	}

	/**
	 * @param photoPath the photoPath to set
	 */
	public static void setPhotoPath(String photoPath) {
		CameraHelper.photoPath = photoPath;
	}
}
