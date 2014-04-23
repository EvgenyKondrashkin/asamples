package com.example.asamles.app.saveload;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class SaveLoadFile {
    static SharedPreferences sPref;
    static SharedPreferences.Editor ed;
	private static final String COUNTER = "COUNTER";
	private static final String DRAWING = "Drawing";	

    public static String setName(Context context) {
        sPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        int counter = sPref.getInt(COUNTER, 0);
        String name = DRAWING + counter + ".png";
        counter++;
        ed = sPref.edit();
        ed.putInt(COUNTER, counter);
        ed.commit();
        return name;
    }

    public static void saveToPrivateGallery(Context context, Bitmap bitmap, String fileName) {
        // showProgressDialogFragment();
        if (fileName == null) {
            fileName = setName(context);
        }
        String imgSaved = MediaStore.Images.Media.insertImage(
                context.getContentResolver(), bitmap,
                fileName, context.getString(R.string.save_tag));
        if (imgSaved != null) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveToPublicGallery(Context context, Bitmap bitmap, String dirName, String fileName, boolean toGallery) {
        if (fileName == null) {
            fileName = setName(context);
        }
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
        File mypath = new File(directory, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mypath);
            ;
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
        }
        if (toGallery) {
            galleryAddPic(context, mypath);
        }
    }

    public static void saveToAppPrivateGallery(Context context, Bitmap bitmap, String dirName, String fileName) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir(dirName, Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mypath);
            ;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
        }
    }

    public static String[] loadAllAppPrivateFiles(Context context, String dirName) {
        String[] savedFiles = context.getApplicationContext().getDir(dirName, Context.MODE_PRIVATE).list();
        return savedFiles;
    }

    public static String[] loadAllPublicFiles(Context context, String dirName) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
        String[] savedFiles = directory.list();

        return savedFiles;
    }

    public static Bitmap loadImageFromPrivateStorage(Context context, String dirName, String name) {
        Bitmap bitmap = null;
        try {
            File file = new File(context.getApplicationContext().getDir(dirName, Context.MODE_PRIVATE), name);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap loadImageFromPublicStorage(String dirName, String name) {
        Bitmap bitmap = null;
        try {
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
            File file = new File(directory, name);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String loadPathFromGallery(Context context, Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImageUri, projection, null, null, null);
        String picturePath = null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            picturePath = cursor.getString(column_index);
        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.load_negative_result), Toast.LENGTH_SHORT).show();
        }
        return picturePath;
    }
    public static void removeFileFromPublicGallery(String dirName, String name){
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
        File image = new File(directory, name);
        image.delete();
    }
    private static void galleryAddPic(Context context, File mypath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(mypath);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}