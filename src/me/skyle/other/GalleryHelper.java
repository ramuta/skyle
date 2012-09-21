package me.skyle.other;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class GalleryHelper {
	private static final String TAG = "GalleryHelper";
	
	private static String imagePath;

	// And to convert the image URI to the direct file system path of the image file
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        // can post image
        String [] proj = {MediaStore.Images.Media.DATA};
        
        Cursor cursor = context.getContentResolver().query(contentUri,
                        proj, // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

	/**
	 * @return the imagePath
	 */
	public static String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public static void setImagePath(String imagePath) {
		GalleryHelper.imagePath = imagePath;
	}
}
