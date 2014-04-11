package com.example.asamles.app.saveload;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class SaveLoadFile {
	static SharedPreferences sPref;
    static SharedPreferences.Editor ed;
	static boolean saveBoth = false;
    public static String setName(Context context) {
		sPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        int counter = sPref.getInt("COUNTER", 0);
		String name = "Drawing"+counter+".png";
		counter++;
		ed = sPref.edit();
		ed.putInt("COUNTER", counter);
        ed.commit();
		return name;
    }
    public static void saveToGallaryAndApp(Context context, Bitmap bitmap) {
        String fileName = setName(context);
		saveBoth = true;
        saveToPaintGallery(context, bitmap, fileName);
        saveToGallery(context, bitmap, fileName);
    }
	public static void saveToGallery(Context context, Bitmap bitmap, String fileName) {
        // showProgressDialogFragment();
        if (fileName == null) {
            fileName = UUID.randomUUID().toString() + ".png";
        }
        String imgSaved = MediaStore.Images.Media.insertImage(
                context.getContentResolver(), bitmap,
                fileName, context.getString(R.string.save_tag));
        if(saveBoth != true) {
			if (imgSaved != null) {
				Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
			}
		} else { saveBoth = false; }
    }
    public static void saveToPaintGallery(Context context, Bitmap bitmap, String fileName) {
//        drawView.setDrawingCacheEnabled(true);
//        drawView.buildDrawingCache(true);
//        Bitmap bitmap = drawView.getDrawingCache();
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("PaintGallery", Context.MODE_PRIVATE);
        File mypath=new File(directory, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mypath);;
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
            fos.close();
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
			Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
        }
//        drawView.setDrawingCacheEnabled(false);
    }

    public static String[] loadAllFiles(Context context){
        String[] savedFiles = context.getApplicationContext().getDir("PaintGallery", Context.MODE_PRIVATE).list();
		// ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
		// File directory = cw.getDir("PaintGallery", Context.MODE_PRIVATE);
		// for(int i = 0; i < savedFiles.length; i++) {
			// savedFiles[i] = directory.getAbsolutePath()+"/"+savedFiles[i];
		// }
        return savedFiles;
    }
    public static Bitmap loadImageFromStorage(Context context, String name)
    {
        Bitmap b = null;
        try {
            File f=new File(context.getApplicationContext().getDir("PaintGallery", Context.MODE_PRIVATE), name);
             b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }

    public static String loadFromGallery(Context context, Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImageUri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
        String picturePath = null;
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            picturePath = cursor.getString(column_index);
        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.load_negative_result), Toast.LENGTH_SHORT).show();
        }
        return picturePath;
    }

}