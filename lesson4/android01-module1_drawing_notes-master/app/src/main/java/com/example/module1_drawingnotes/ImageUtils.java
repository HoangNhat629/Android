package com.example.module1_drawingnotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    private static String folderName = "DrawingNotes";

    public static void saveImage(Bitmap bitmap, Context context) {
        //1. create new folder to save image
        String root = Environment.getExternalStorageDirectory().toString();
        Log.d(TAG, "saveImage: root " + root);

        File folder = new File(root, folderName);
        folder.mkdir();

        //2. create empty file (.png)
        String imageName = Calendar.getInstance().getTime().toString() + ".png";
        Log.d(TAG, "saveImage: imageName " + imageName);

        File imageFile = new File(folder, imageName);
        Log.d(TAG, "saveImage: " + imageFile.getAbsolutePath());

        //3. save image
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();

            //4. scan to make image appear immediately in gallery
            MediaScannerConnection.scanFile(context,
                    new String[]{imageFile.getAbsolutePath()},
                    null,
                    null);

        } catch (FileNotFoundException e) {
            Log.d(TAG, "saveImage: FileNotFoundException");
        } catch (IOException e) {
            Log.d(TAG, "saveImage: IOException");
        }
    }
    public static List<File> getListImage() {
        List<File> images = new ArrayList<>();
        File folder = new File(Environment.getExternalStorageDirectory().toString(), folderName);
        if (folder.listFiles() != null) {
            images = Arrays.asList(folder.listFiles());
        }
        return images;
    }

}
